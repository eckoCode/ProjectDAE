<template>
  <v-app>
    <v-card>
      <v-card-title>Clients
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search by company name" single-line hide-details></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="clients" :search="search">
        <template slot="items" slot-scope="props">
                          <td>{{ props.item.companyName }}</td>
                          <td class="text-xs-left">{{ props.item.address }}</td>
                          <td class="text-xs-left">{{ props.item.contactPerson }}</td>
                          <td class="text-xs-left">{{ props.item.username }}</td>
                           <td class="text-xs-left">{{ props.item.email }}</td>
                          <td class="justify-center layout px-0">
                        <v-icon small class="mr-2" @click="editClient(props.item)">edit</v-icon>
                        <v-icon small @click="deleteClient(props.item)">delete </v-icon>
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
            <span class="headline">Edit client</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedClient.companyName" label="Company name"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedClient.address" label="Address"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedClient.contactPerson" label="Contact person"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field disabled v-model="editedClient.username" label="Username"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field disabled v-model="editedClient.email" label="Email"></v-text-field>
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
        headers: [{
            text: "Company name",
            align: "left",
            sortable: true,
            value: "companyName"
          },
          {
            text: "Address",
            sortable: true,
          },
          {
            text: "Contact person",
            sortable: true,
          },
          {
            text: "Username",
            sortable: true,
          },
           {
            text: "Email",
            sortable: true,
          },
          {
            text: "Actions"
          }
        ],
        clients: [],
        dialog: false,
        editedClient: {
          contactName: '',
          address: '',
          contactPerson: '',
          username: '',
          email: ''
        }
      };
    },
    created() {
      this.fetchClients();
    },
    methods: {
      fetchClients: function() {
          this.$axios.get('api/clients', {auth:{username:this.$axios.defaults.auth.username, password:this.$axios.defaults.auth.password}})
          .then(response => {
            console.log(response);
            this.clients = response.data;
          })
          .catch(error => {
            console.log(error);
          });

      
      },
      deleteClient: function(client) {
        console.log(client);
        this.$axios.delete('api/clients/' + client.username).then(response => {
          this.fetchClients();
          console.log("client deleted")
        }).catch(error => {
          console.log(error);
        })
      },
      editClient(client) {
        Object.assign(this.editedClient, client);
        this.dialog = true;
        console.log(this.editedClient);
      },
      close() {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
  
      save() {
        this.$axios.put('api/clients' , this.editedClient).then(response => {
          this.fetchClients();
          this.close()
          console.log("Client edited")
        }).catch(error => {
          console.log(error.message);
        })
      }
    }
  };
</script>
