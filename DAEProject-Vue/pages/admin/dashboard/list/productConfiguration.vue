<template>
  <v-app>
    <v-card>
      <v-card-title>Products configuration
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search by name" single-line hide-details></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="products" :search="search">
        <template slot="items" slot-scope="props">
                                  <td class="text-xs-left">{{ props.item.name }}</td>
                                  <td class="text-xs-left">{{ props.item.baseVersion }}</td>
                                  <td class="text-xs-left">{{ props.item.license }}</td>
                                  <td class="text-xs-left">{{ props.item.parameterization }}</td>
                                  <td class="text-xs-left">{{ props.item.supportMaterials }}</td>
                                  <td class="text-xs-left">{{ props.item.description }}</td>
                                  <td class="text-xs-left">{{ props.item.baseCode }}</td>
                                  <td class="text-xs-left">{{ props.item.softwareState }}</td>
                                  <td class="text-xs-left">{{ props.item.hardwareRequired }}</td>
                                  <td class="text-xs-left">{{ props.item.contractData }}</td>
                                  <td class="justify-center layout px-0">
                                <v-icon small class="mr-2" @click="editProduct(props.item)">edit</v-icon>
                                <v-icon small @click="deleteProduct(props.item)">delete </v-icon>
                              </td>
        </template>
        <v-alert
          slot="no-results"
          :value="true"
          color="error"
          icon="warning"
        >Your search for "{{ search }}" found no results.</v-alert>
      </v-data-table>
    </v-card>

    <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">Edit product configuration</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedProductConfiguration.name" label="Name"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedProductConfiguration.baseVersion" label="Base version"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedProductConfiguration.license" label="License"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedProductConfiguration.parametrization" label="Parametrization"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedProductConfiguration.supportMaterials" label="Support material"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedProductConfiguration.description" label="Description"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field  v-model="editedProductConfiguration.baseCode" label="Base code"></v-text-field>
                </v-flex>
                  <v-flex xs12 sm6 md4>
                  <v-text-field  v-model="editedProductConfiguration.softwareState" label="Software state"></v-text-field>
                </v-flex>  <v-flex xs12 sm6 md4>
                  <v-text-field  v-model="editedProductConfiguration.hardwareRequired" label="Hardware required"></v-text-field>
                </v-flex>  <v-flex xs12 sm6 md4>
                  <v-text-field  v-model="editedProductConfiguration.contractData" label="Contract data"></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
            <v-btn color="blue darken-1" flat @click="save">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
  </v-app>
</template>

<script>
  export default {
    data() {
      return {
        search: "",
        headers: [
          {
            text: "Name",
            sortable: true,
          },
          {
            text: "Base version",
            sortable: true,
          },
          {
            text: "License",
            sortable: true,
          },
          {
            text: "Parametrization",
            sortable: true,
          }, {
            text: "Support material",
            sortable: true,
          }, {
            text: "Description",
            sortable: true,
          }, {
            text: "Base code",
            sortable: true,
          },
          {
            text: "Software state",
            sortable: true,
          }, {
            text: "Hardware required",
            sortable: true,
          },
          {
            text: "Contract data",
            sortable: true,
          },
          {
            text: "Actions"
          }
        ],
        products: [],
        dialog: false,
        editedProductConfiguration: {
          name:'',
          baseVersion:'',
          license:'',
          parametrization:'',
          supportMaterials:'',
          description:'',
          baseCode:'',
          softwareState:'',
          hardwareRequired:'',
          contractData:''
        }
      };
    },
    created() {
      this.fetchProducts();
    },
    methods: {
      fetchProducts: function() {
        this.$axios.get('api/products')
          .then(response => {
            console.log(response);
            this.products = response.data;
          })
          .catch(error => {
            console.log(error);
          });
      },
      deleteProduct: function(product) {
        this.$axios.delete('api/products/' + product.name).then(response => {
          this.fetchProducts();
          console.log("product deleted")
        }).catch(error => {
          console.log(error);
        })
      },
      editProduct(product) {
        Object.assign(this.editedProductConfiguration, product);
        this.dialog = true;
        console.log(this.editedProductConfiguration);
      },
      close() {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
  
      save() {
        this.$axios.put('api/products/' + this.editedProductConfiguration.name).then(response => {
          this.fetchProducts();
          this.close()
          console.log("Product edited")
        }).catch(error => {
          console.log(error.message);
        })
      }
    }
  };
</script>
