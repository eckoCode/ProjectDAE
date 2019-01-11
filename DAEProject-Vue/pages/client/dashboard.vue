<template>
  <v-app>
    <v-card>
      <v-card-title>Products configuration
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="search"
          label="Search by name"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="products" :search="search">
        <template slot="items" slot-scope="props">
          <td class="text-xs-left">{{ props.item.username }}</td>
          <td class="text-xs-left">{{ props.item.description }}</td>
          <td class="text-xs-left">{{ props.item.name }}</td>
          <td class="text-xs-left">{{ props.item.license }}</td>
          <td class="text-xs-left">{{ props.item.stateOfSoftware }}</td>
          <td class="text-xs-left">
            <v-menu offset-y>
              <v-btn small class="mr-2" slot="activator" color="primary" dark>Contract Info</v-btn>
              <v-list>
                <v-list-tile>
                  <v-list-tile-title>Info: {{ props.item.contract.contractInfo }}</v-list-tile-title>
                </v-list-tile>
                <v-list-tile>
                  <v-list-tile-title>Client ID: {{ props.item.contract.idClient }}</v-list-tile-title>
                </v-list-tile>
              </v-list>
            </v-menu>
          </td>
          <td class="text-xs-left">{{ props.item.hardwareRequired }}</td>
          <td class="text-xs-left">
            <v-menu offset-y>
              <v-btn small class="mr-2" slot="activator" color="primary">Support Materials</v-btn>
              <v-list>
                <v-list-tile v-for=" material in props.item.supportMatterials" :key="material">
                  <v-list-tile-title>Material: {{ material }}</v-list-tile-title>
                  <v-btn @click="downloadFile(material)">Download</v-btn>
                </v-list-tile>
              </v-list>
            </v-menu>
          </td>
          <td>
            <v-btn
              small
              class="mr-2"
              @click="showSoftware()"
              color="primary"
              dark
              slot="activator"
            >Software</v-btn>
            <v-dialog v-model="dialogSoftware" scrollable max-width="500px">
              <v-card>
                <v-card-title>
                  <span class="headline">List Software</span>
                </v-card-title>
                <v-card-text>
                  <v-container grid-list-md>
                    <v-layout wrap v-for="software in props.item.softwares" :key="software.id">
                      <span class="headline">Software:</span>
                      <br>
                      <v-flex xs12 sm6 md4>
                        <v-card>
                          <v-card-text>Name: {{software.name}}</v-card-text>
                        </v-card>
                      </v-flex>
                      <v-flex xs12 sm6 md4>
                        <v-card>
                          <v-card-text>Version: {{software.version}}</v-card-text>
                        </v-card>
                      </v-flex>
                      <v-flex>
                        <span class="headline">Artifacts:</span>
                        <br>
                        <v-list v-for="artifact in software.artifacts" :key="artifact.id">
                          <v-list-tile>
                            <v-list-tile-title v-if="artifact.libraries ===''">Libraries: ------</v-list-tile-title>
                            <v-list-tile-title>Libraries: {{ artifact.libraries }}</v-list-tile-title>
                          </v-list-tile>
                          <v-list-tile>
                            <v-list-tile-title v-if="artifact.scripts === ''">Scripts: ------</v-list-tile-title>
                            <v-list-tile-title v-else>Scripts: {{ artifact.scripts }}</v-list-tile-title>
                          </v-list-tile>
                          <v-list-tile>
                            <v-list-tile-title>SourceCode: {{ artifact.sourceCode }}</v-list-tile-title>
                          </v-list-tile>
                          <v-list-tile>
                            <v-list-tile-title v-if="artifact.database === ''">Database: ------</v-list-tile-title>
                            <v-list-tile-title v-else>Database: {{ artifact.database }}</v-list-tile-title>
                          </v-list-tile>
                          <v-divider></v-divider>
                        </v-list>
                      </v-flex>
                    </v-layout>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </td>
          <td>
            <v-btn
              small
              class="mr-2"
              @click="showModules()"
              color="primary"
              dark
              slot="activator"
            >Modules</v-btn>
            <v-dialog v-model="dialogModules" scrollable max-width="500px">
              <v-card>
                <v-card-title>
                  <span class="headline">Modules</span>
                </v-card-title>
                <v-card-text>
                  <v-container grid-list-lg>
                    <v-layout wrap v-for="module in props.item.modules" :key="module.id">
                      <v-flex xs12 sm6 md4>
                        <v-card>
                          <v-card-text>Parameterization: {{module.parametrization}}</v-card-text>
                        </v-card>
                      </v-flex>
                      <v-flex>
                        <v-list v-for="extension in module.extension" :key="extension.id">
                          <v-list-tile>
                            <v-list-tile-title>Name: {{ extension.name }}</v-list-tile-title>
                          </v-list-tile>
                          <v-list-tile>
                            <v-list-tile-title>Version: {{ extension.version }}</v-list-tile-title>
                          </v-list-tile>
                          <v-list-tile>
                            <v-btn @click="dialogArtifacts = true">List Artifacts</v-btn>
                            <v-dialog v-model="dialogArtifacts" scrollable max-width="300px">
                              <v-list v-for="artifact in extension.artifacts" :key="artifact.id">
                                <v-list-tile>
                                  <v-list-tile-title
                                    v-if="artifact.libraries ===''"
                                  >Libraries: ------</v-list-tile-title>
                                  <v-list-tile-title>Libraries: {{ artifact.libraries }}</v-list-tile-title>
                                </v-list-tile>
                                <v-list-tile>
                                  <v-list-tile-title v-if="artifact.scripts === ''">Scripts: ------</v-list-tile-title>
                                  <v-list-tile-title v-else>Scripts: {{ artifact.scripts }}</v-list-tile-title>
                                </v-list-tile>
                                <v-list-tile>
                                  <v-list-tile-title>SourceCode: {{ artifact.sourceCode }}</v-list-tile-title>
                                </v-list-tile>
                                <v-list-tile>
                                  <v-list-tile-title
                                    v-if="artifact.database === ''"
                                  >Database: ------</v-list-tile-title>
                                  <v-list-tile-title v-else>Database: {{ artifact.database }}</v-list-tile-title>
                                </v-list-tile>
                                <v-divider></v-divider>
                              </v-list>
                            </v-dialog>
                          </v-list-tile>
                          <v-divider></v-divider>
                        </v-list>
                      </v-flex>
                    </v-layout>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogQuestion" persistent max-width="600px">
			<v-card>
				<v-card-title>
				<span class="headline">Your Question</span>
				</v-card-title>
				<v-card-text>
				<v-container grid-list-md>
					<v-layout wrap>
					<v-flex xs12>
						<v-text-field v-model="editedQa.question" label="Your Question*" required></v-text-field>
					</v-flex>
					</v-layout>
				</v-container>
				</v-card-text>
				<v-card-actions>
				<v-spacer></v-spacer>
				<v-btn color="blue darken-1" flat @click="dialogQuestion = false">Close</v-btn>
				<v-btn color="blue darken-1" flat  @click="saveQA">Save</v-btn>
				</v-card-actions>
			</v-card>
			</v-dialog>
          </td>
          <td class="justify-center layout px-0">
						<v-icon small class="mr-2" @click="editQa(props.item)">info</v-icon>
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
  </v-app>
</template>
<script>
export default {
  data() {
    return {
      search: "",
      contractDialog: false,
      listSoftware: [],
      listOfModules: [],
      arraySoftState: ["ACTIVE", "INACTIVE", "SUSPENDED"],
      arrayHardware: ["Cloud", "Server", "Laptop"],
      headers: [
        {
          text: "Client Name",
          sortable: true,
          value: "username"
        },
        {
          text: "Description",
          sortable: true
        },
        {
          text: "Product Config Name",
          sortable: true
        },
        {
          text: "License",
          sortable: true
        },
        {
          text: "Software state",
          sortable: true
        },
        {
          text: "Contract Info",
          sortable: true
        },
        {
          text: "Hardware required",
          sortable: true
        },
        {
          text: "Support Materials",
          sortable: true
        },
        {
          text: "Software",
          sortable: true
        },
        {
          text: "Modules",
          sortable: true
        },
        {
          text: "Actions",
          sortable: true
        }
      ],
      products: [],
      dialogSoftware: false,
      dialogModules: false,
      dialogArtifacts: false,
      editedQa: {
					id: 0,
					answer: "",
					question: "",
					prodConfig: {
					}
				},
				dialogQuestion: false
    };
  },
  created() {
    this.fetchProducts();
  },
  methods: {
    showModules() {
      this.dialogSoftware = false;
      this.dialogModules = true;
    },
    showSoftware() {
      this.dialogSoftware = true;
      this.dialogModules = false;
    },
    close() {
      this.dialogSoftware = false;
      this.dialogModules = false;
      this.dialogQuestion = false;
    },
    fetchProducts: function() {
      this.$axios
        .get("api/productConfig/client/" + this.$axios.defaults.auth.username, {
          auth: {
            username: this.$axios.defaults.auth.username,
            password: this.$axios.defaults.auth.password
          }
        })
        .then(response => {
          console.log(response.data);
          this.products = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    	downloadFile(material) {
				var split_material =material.split('/');
				this.$axios({
					url: "api/productConfig/getFile/"+split_material[split_material.length-1],
					method: 'GET',
					responseType: 'blob'
				}, {
					auth: {
						username: this.$axios.defaults.auth.username,
						password: this.$axios.defaults.auth.password
					}
				}).then(response => {
					console.log(response.data);
					const url = window.URL.createObjectURL(new Blob([response.data]));
					const link = document.createElement('a');
					link.href = url;
					link.setAttribute('download', split_material[5]+'.pdf');
					document.body.appendChild(link);
					link.click();
					console.log(link)
				})
					.catch(error => {
						console.log(error);
					});
      },
      editQa(qa) {
				console.log("Prod Config",qa);
				Object.assign(this.editedQa.prodConfig, qa);
				this.dialogQuestion = true;
				console.log("Edited QA",this.editedQa);

      },
      saveQA(){
				this.$axios.post('api/qa/create', this.editedQa)
				.then(response => {
					console.log("Question asked");
				})
				.catch(error=>{
					console.log("Question failed");
				})
			}
  }
};
</script>
