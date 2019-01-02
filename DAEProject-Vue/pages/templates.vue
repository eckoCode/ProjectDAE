<template>
  <v-app>
    <v-card>
      <v-card-title>Templates
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search by name" single-line
                      hide-details></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="templates" :search="search">
        <template slot="items" slot-scope="props">
          <td class="text-xs-left">{{ props.item.id }}</td>
          <td class="text-xs-left">{{ props.item.description }}</td>
          <td class="justify-center layout px-0">
            <v-icon small class="mr-2" @click="editTemplate(props.item)">edit</v-icon>
            <v-icon small @click="deleteTemplate(props.item)">delete</v-icon>
          </td>
        </template>
        <v-alert
          slot="no-results"
          :value="true"
          color="error"
          icon="warning"
        >Your search for "{{ search }}" found no results.
        </v-alert>
      </v-data-table>
    </v-card>

    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ formTitle }}</span>
        </v-card-title>

        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="editedTemplate.id" label="Id"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md6>
                <v-text-field v-model="editedTemplate.description" label="Description"></v-text-field>
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
            text: "Id",
            align: "left",
            sortable: true,
            value: "id"
          },
          {
            text: "Description",
            sortable: true,
            value: "description"
          },
          {
            text: "Actions"
          }
        ],
        templates: [],
        dialog: false,
        editedIndex: -1,
        editedTemplate: {
          id: '',
          description: ''
        },
        defaultTemplate: {
          id: '',
          description: ''
        }
      };
    },
    created() {
      this.fetchTemplates();
    },
    computed: {
        formTitle () {
          return this.editedIndex === -1 ? 'New Template' : 'Edit Template'
      }
    },

    watch: {
      dialog (val) {
        val || this.close()
      }
    },
    methods: {
      fetchTemplates: function () {
        this.$axios.get('api/templates')
          .then(response => {
            console.log(response);
            this.templates = response.data;
          })
          .catch(error => {
            console.log(error);
          });
      },
      deleteTemplate: function (template) {
        console.log(template);
        this.$axios.delete('api/templates/' + template.id).then(response => {
          this.fetchTemplates();
          console.log("Template deleted")
        }).catch(error => {
          console.log(error);
        })
      },
      editTemplate(template) {
        //Object.assign(this.editedTemplate, template);
        this.editedTemplate.id = template.id;
        this.editedTemplate.description = template.description;
        this.dialog = true;
        console.log(this.editedTemplate);
      },
      close() {
        this.dialog = false
        setTimeout(() => {
          this.editedTemplate = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },

      save() {
        console.log("A Gravar este: ", this.editedTemplate);
          this.$axios.put('api/templates/',this.editedTemplate)
          .then(response => {
            this.fetchTemplates();
            console.log("Template edited")
          }).catch(error => {
            console.log("EUUUUUU",error.message);
          });
          //Object.assign(this.templates[this.editedIndex], this.editedTemplate)
		      this.close();
      }
    }
  };
</script>
