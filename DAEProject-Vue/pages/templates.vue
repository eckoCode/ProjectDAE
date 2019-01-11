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
			<td class="text-xs-left">{{ props.item.name }}</td>
			<td class="text-xs-left">{{ props.item.description }}</td>
			<td class="text-xs-left">{{ props.item.hardware }}</td>
			<td class="text-xs-left">{{ props.item.license }}</td>
			<td class="text-xs-left">
				<v-menu offset-y>
					<v-btn
						small class="mr-2"
						slot="activator"
						color="primary"
						dark
					>
						Support Materials
					</v-btn>
					<v-list>
						<v-list-tile v-for=" material in props.item.supportMaterials" :key="material">
							<v-list-tile-title>Material: {{ material }}</v-list-tile-title>
							<v-btn @click="downloadFile(material)">Download</v-btn>
						</v-list-tile>
					</v-list>
				</v-menu>
			</td>
			<td class="text-xs-left">
				<v-btn
					small class="mr-2" @click="dialogSoftware = true"
					color="primary"
					dark
					slot="activator"
				>
					Software
				</v-btn>
				<v-dialog v-model="dialogSoftware" scrollable max-width="500px">
					<v-card>
						<v-card-title>
							<span class="headline">List Software</span>
						</v-card-title>
						<v-card-text>
							<v-container grid-list-md>
								<v-layout wrap v-for="software in props.item.software" :key="software.id">
									<span class="headline">Software: </span>
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
										<span class="headline">Artifacts: </span>
										<br>
										<v-list v-for="artifact in software.artifacts" :key="artifact.id">
											<v-list-tile>
												<v-list-tile-title v-if="artifact.libraries ===''">Libraries:
													------
												</v-list-tile-title>
												<v-list-tile-title>Libraries: {{ artifact.libraries }}
												</v-list-tile-title>
											</v-list-tile>
											<v-list-tile>
												<v-list-tile-title v-if="artifact.scripts === ''">Scripts:
													------
												</v-list-tile-title>
												<v-list-tile-title v-else>Scripts: {{ artifact.scripts }}
												</v-list-tile-title>
											</v-list-tile>
											<v-list-tile>
												<v-list-tile-title>SourceCode: {{ artifact.sourceCode }}
												</v-list-tile-title>
											</v-list-tile>
											<v-list-tile>
												<v-list-tile-title v-if="artifact.database === ''">Database:
													------
												</v-list-tile-title>
												<v-list-tile-title v-else>Database: {{ artifact.database }}
												</v-list-tile-title>
											</v-list-tile>
											<v-divider></v-divider>
										</v-list>
									</v-flex>
								</v-layout>
							</v-container>
						</v-card-text>
						<v-card-actions>
							<v-btn color="blue darken-1" flat @click="dialogSoftware = false">Cancel</v-btn>
						</v-card-actions>
					</v-card>
				</v-dialog>
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
				text: "Name",
				sortable: true,
				value: "name"
			},
			{
				text: "Description",
				sortable: true,
				value: "description"
			},
			{
				text: "Hardware",
				sortable: true,
				value: "hardware"
			},
			{
				text: "License",
				sortable: true,
				value: "license"
			},
			{
				text: "Support Materials",
				sortable: true,
			},
			{
				text: "Software",
				sortable: true,
			},
        ],
        templates: [],
        dialog: false,
		  dialogSoftware: false,
        editedIndex: -1,
      };
    },
    created() {
      this.fetchTemplates();
    },
    computed: {

    },
    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogSoftware (val) {
        val || this.close()
      }
    },
    methods: {
		downloadFile(material) {
			var split_material =material.split('/');
			this.$axios({
				url: "api/templates/getFile/"+split_material[split_material.length-1],
				method: 'GET',
				responseType: 'blob'
			}, 
			).then(response => {
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
      close() {
        this.dialog = false;
        this.dialogSoftware = false;
        setTimeout(() => {
          this.editedTemplate = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
    }
  };
</script>
