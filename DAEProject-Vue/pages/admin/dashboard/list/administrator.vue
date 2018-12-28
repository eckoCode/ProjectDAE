<template>
  <v-app>
    <v-card>
      <v-card-title>Administrators
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search by name" single-line hide-details></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="admins" :search="search">
        <template slot="items" slot-scope="props">
                          <td>{{ props.item.name }}</td>
                          <td class="text-xs-left">{{ props.item.position }}</td>
                          <td class="text-xs-left">{{ props.item.email }}</td>
                          <td class="text-xs-left">{{ props.item.username }}</td>
                          <td class="justify-center layout px-0">
                        <v-icon small class="mr-2" @click="editAdmin(props.item)">edit</v-icon>
                        <v-icon small @click="deleteAdmin(props.item)">delete </v-icon>
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
            <span class="headline">Edit admin</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedAdmin.name" label="Name"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedAdmin.position" label="Position"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedAdmin.email" label="Email"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field disabled v-model="editedAdmin.username" label="Username"></v-text-field>
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
            text: "Name",
            align: "left",
            sortable: true,
            value: "name"
          },
          {
            text: "Position",
            sortable: true,
            value: "calories"
          },
          {
            text: "Email",
            sortable: true,
            value: "fat"
          },
          {
            text: "Username",
            sortable: true,
            value: "carbs"
          },
          {
            text: "Actions"
          }
        ],
        admins: [],
        dialog: false,
        editedAdmin: {
          name: '',
          position: '',
          email: '',
          username: '',
        }
      };
    },
    created() {
      this.fetchAdmins();
    },
    methods: {
      fetchAdmins: function() {
        this.$axios.get('api/admins')
          .then(response => {
            console.log(response.data);
            this.admins = response.data;
          })
          .catch(error => {
            console.log(error);
          });
      },
      deleteAdmin: function(admin) {
        console.log(admin);
        this.$axios.delete('api/admins/' + admin.username).then(response => {
          this.fetchAdmins();
          console.log("Admin deleted")
        }).catch(error => {
          console.log(error);
        })
      },
      editAdmin(admin) {
        Object.assign(this.editedAdmin, admin);
        this.dialog = true;
        console.log(this.editedAdmin);
      },
      close() {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
  
      save() {
        this.$axios.put('api/admins/', this.editedAdmin).then(response => {
          this.fetchAdmins();
          this.close()
          console.log("Admin edited")
        }).catch(error => {
          console.log(error.message);
        })
      }
    }
  };
</script>
