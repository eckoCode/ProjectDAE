<template>
	<v-app>
		<v-card>
			<v-card-title>Templates
				<v-spacer></v-spacer>
				<v-text-field v-model="search" append-icon="search" label="Search" single-line
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
					<span class="headline">Edit Template</span>
				</v-card-title>
				<v-card-text>
					<v-container grid-list-md>
						<v-layout wrap>
							<v-flex xs12 sm6 md6>
								<v-text-field v-model="editedTemplate.name" label="Name"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md6>
								<v-text-field v-model="editedTemplate.description" label="Description"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md6>
								<v-select :items="listOfHardware" v-model="editedTemplate.hardware" item-text="hardware"
										  label="Hardware"></v-select>
							</v-flex>
							<v-flex xs12 sm6 md6>
								<v-text-field v-model="editedTemplate.license" label="License"></v-text-field>
							</v-flex>
							<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
								<v-text-field label="Select File" @click="pickFile" v-model="baseCode"
											  prepend-icon="attach_file"></v-text-field>
								<input type="file" style="display: none" ref="image"
									   accept="application/pdf"
									   @change="onFilePicked">
							</v-flex>
							<v-container fluid>
								<v-card>
									<v-card-text>List of Softwares
									</v-card-text>
									<v-layout row wrap>
										<v-flex xs12 sm4 md4 v-for="soft in listSoftware" :key="soft.id">
											<v-checkbox v-model="editedTemplate.software" :label=soft.name
														:value=soft></v-checkbox>
										</v-flex>
									</v-layout>
								</v-card>
							</v-container>
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
				listOfHardware: [
					'Cloud',
					'Server',
					'Laptop',
				],
				headers: [
					{
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
					{
						text: "Actions"
					}
				],
				baseCode: "",
				baseCodeURL: "",
				baseCodeFile: "",
				templates: [],
				dialog: false,
				dialogSoftware: false,
				editedIndex: -1,
				editedTemplate: {
					id: '',
					name: '',
					description: '',
					hardware: '',
					license: '',
					software: [],
				},
				defaultTemplate: {
					name: '',
					description: '',
					hardware: '',
					license: '',
					software: [],
				},
				listSoftware: [],
			};
		},
		mounted() {
			this.fetchTemplates();
			this.fetchSoftware();
		},
		computed: {
			formTitle() {
				return this.editedIndex === -1 ? 'New Template' : 'Edit Template'
			}
		},

		watch: {
			dialog(val) {
				val || this.close()
			}
		},
		methods: {
			fetchTemplates: function () {
				this.$axios.get('api/templates', {
					auth: {
						username: this.$axios.defaults.auth.username,
						password: this.$axios.defaults.auth.password
					}
				})
					.then(response => {
						console.log("templates", response);
						this.templates = response.data;
					})
					.catch(error => {
						console.log(error);
					});
			},
			fetchSoftware: function () {
				this.$axios.get('api/software', {
					auth: {
						username: this.$axios.defaults.auth.username,
						password: this.$axios.defaults.auth.password
					}
				})
					.then(response => {
						console.log(" Softwares", response);
						this.listSoftware = response.data;
					})
					.catch(error => {
						console.log(error);
					});
			},
			deleteTemplate: function (template) {
				console.log(template);
				this.$axios.delete('api/templates/delete/' + template.id).then(response => {
					this.fetchTemplates();
					console.log("Template deleted")
				}).catch(error => {
					console.log(error);
				})
			},
			editTemplate(template) {
				//Object.assign(this.editedTemplate, template);
				this.editedTemplate.id = template.id;
				this.editedTemplate.name = template.name;
				this.editedTemplate.description = template.description;
				this.editedTemplate.hardware = template.hardware;
				this.editedTemplate.license = template.license;
				this.editedTemplate.supportMaterials = template.supportMaterials;
				this.editedTemplate.software = template.software;
				this.dialog = true;
				console.log(this.editedTemplate);
			},
			close() {
				this.dialog = false;
				setTimeout(() => {
					this.editedTemplate = Object.assign({}, this.defaultItem);
					this.editedIndex = -1
				}, 300)
			},
			save() {
				console.log("A Gravar este: ", this.editedTemplate);
				this.$axios.put('api/templates/update', this.editedTemplate)
					.then(response => {
						var formData = new FormData();
						formData.append("baseCode", this.baseCodeFile);
						console.log("resposta", response.data);
						this.$axios
							.post(
								"api/templates/supportMaterials/" + this.baseCodeFile.name + "/" + this.editedTemplate.id,
								formData,
								{
									auth: {
										username: this.$axios.defaults.auth.username,
										password: this.$axios.defaults.auth.password
									}
								},
								{
									headers: {
										"Content-Type": "multipart/form-data"
									}
								}
							)
							.then(response => {
								console.log("upload");
								this.fetchTemplates();
								this.fetchSoftware();
							})
							.catch(error => {
								console.log(error);
							});
						console.log("Template edited")
					}).catch(error => {
					console.log("EUUUUUU", error.message);
				});
				//Object.assign(this.templates[this.editedIndex], this.editedTemplate)
				this.close();
			},
			clear() {
				this.$refs.form.reset();
			},
			pickFile() {
				this.$refs.image.click();
			},
			onFilePicked(e) {
				const files = e.target.files;
				if (files[0] !== undefined) {
					this.baseCode = files[0].name;
					if (this.baseCode.lastIndexOf(".") <= 0) {
						return;
					}
					const fr = new FileReader();
					fr.readAsDataURL(files[0]);
					fr.addEventListener("load", () => {
						this.baseCodeURL = fr.result;
						this.baseCodeFile = files[0]; // this is a file that can be sent to server...
					})

				} else {
					this.baseCode = "";
					this.baseCodeFile = "";
					this.baseCodeURL = "";
				}
			},
			downloadFile(material) {
				var split_material =material.split('/');
				this.$axios({
					url: "api/templates/getFile/"+split_material[split_material.length-1],
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
			}
		}
	};
</script>




