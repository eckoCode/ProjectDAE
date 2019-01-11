<template>
	<v-app>
		<v-card>
			<v-card-title>Products configuration
				<v-spacer></v-spacer>
				<v-text-field v-model="search" append-icon="search" label="Search by name" single-line
							  hide-details></v-text-field>
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
							<v-btn
								small class="mr-2"
								slot="activator"
								color="primary"
								dark
							>
								Contract Info
							</v-btn>
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
							<v-btn
								small class="mr-2"
								slot="activator"
								color="primary"

							>
								Support Materials
							</v-btn>
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
							small class="mr-2" @click="showSoftware()"
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
										<v-layout wrap v-for="software in props.item.softwares" :key="software.id">
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
														<v-list-tile-title v-if="artifact.libraries ===''">Libraries: ------ </v-list-tile-title>
														<v-list-tile-title>Libraries: {{ artifact.libraries }}</v-list-tile-title>
													</v-list-tile>
													<v-list-tile>
														<v-list-tile-title v-if="artifact.scripts === ''">Scripts: ------ </v-list-tile-title>
														<v-list-tile-title v-else>Scripts: {{ artifact.scripts }}</v-list-tile-title>
													</v-list-tile>
													<v-list-tile>
														<v-list-tile-title>SourceCode: {{ artifact.sourceCode }}</v-list-tile-title>
													</v-list-tile>
													<v-list-tile>
														<v-list-tile-title v-if="artifact.database === ''">Database: ------ </v-list-tile-title>
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
							small class="mr-2" @click="showModules()"
							color="primary"
							dark
							slot="activator"
						>
							Modules
						</v-btn>
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
																	<v-list-tile-title v-if="artifact.libraries ===''">Libraries: ------ </v-list-tile-title>
																	<v-list-tile-title>Libraries: {{ artifact.libraries }}</v-list-tile-title>
																</v-list-tile>
																<v-list-tile>
																	<v-list-tile-title v-if="artifact.scripts === ''">Scripts: ------ </v-list-tile-title>
																	<v-list-tile-title v-else>Scripts: {{ artifact.scripts }}</v-list-tile-title>
																</v-list-tile>
																<v-list-tile>
																	<v-list-tile-title>SourceCode: {{ artifact.sourceCode }}</v-list-tile-title>
																</v-list-tile>
																<v-list-tile>
																	<v-list-tile-title v-if="artifact.database === ''">Database: ------ </v-list-tile-title>
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
					</td>
					<td class="text-xs-center">
						<v-btn
							small class="mr-2" @click="cloneProduct(props.item)"
							color="primary"
							dark
						>
							Clone
						</v-btn>
					</td>
					<td class="justify-center layout px-0">
						<v-icon small class="mr-2" @click="editProduct(props.item)">edit</v-icon>
						<v-icon small class="mr-2" @click="editQa(props.item)">info</v-icon>
						<v-icon small @click="deleteProduct(props.item)">delete</v-icon>
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
		<v-dialog v-model="dialog" max-width="500px">
			<v-card>
				<v-card-title>
					<span v-if="showEditUser" class="headline">Edit product configuration</span>
					<span v-if="showSelectUser" class="headline">Clone</span>
				</v-card-title>
				<v-card-text>
					<v-container grid-list-md v-if="showEditUser">
						<v-layout wrap>
							<v-flex xs12 sm6 md4>
								<v-text-field disabled v-model="editedProductConfiguration.username" label="Client Name"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="editedProductConfiguration.name" label="Config Name"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="editedProductConfiguration.description" label="Description"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="editedProductConfiguration.license"
											  label="License"></v-text-field>
							</v-flex>
							<v-select :items="arraySoftState" label="Software State" v-model="editedProductConfiguration.stateOfSoftware"></v-select>
							<v-select :items="arrayHardware" label="Hardware" v-model="editedProductConfiguration.hardwareRequired"></v-select>
							<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
								<v-text-field label="Select a support material" @click="pickFile" v-model="baseCode"
											  prepend-icon="attach_file"></v-text-field>
								<input type="file" style="display: none" ref="image"
									   accept="application/pdf"
									   @change="onFilePicked">
							</v-flex>
							<v-container fluid>
								<v-card>
									<v-card-text>List of Softwares
									</v-card-text>
									<v-layout row wrap >
										<v-flex xs12 sm4 md4 v-for="soft in listSoftware" :key="soft.id">
											<v-checkbox v-model="editedProductConfiguration.softwares" :label=soft.name :value=soft></v-checkbox>
										</v-flex>
									</v-layout>
								</v-card>
							</v-container>
							<v-container fluid>
							<v-card>
								<v-card-text>List of Modules
								</v-card-text>
								<v-layout row wrap>
									<v-flex xs12 sm4 md4 v-for="module in listOfModules" :key="module.id">
										<v-checkbox v-model="editedProductConfiguration.modules" :label=module.parametrization :value=module></v-checkbox>
									</v-flex>
								</v-layout>
							</v-card>
					</v-container>
						</v-layout>
					</v-container>
					<v-container grid-list-md v-if="showSelectUser">		
						<v-layout wrap>
						<v-flex xs12 sm6 md4>
							<v-select :items="clients" v-model="cloneProductConfiguration.username"
										  item-text="username"
										  label="Client"></v-select>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="cloneProductConfiguration.name" label="Config Name"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="cloneProductConfiguration.description" label="Description"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="cloneProductConfiguration.license"
											  label="License"></v-text-field>
							</v-flex>
							<v-select :items="arraySoftState" label="Software State" v-model="cloneProductConfiguration.stateOfSoftware"></v-select>
							<v-select :items="arrayHardware" label="Hardware" v-model="cloneProductConfiguration.hardwareRequired"></v-select>
							<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
								<v-text-field label="Select a support material" @click="pickFile" v-model="baseCode"
											  prepend-icon="attach_file"></v-text-field>
								<input type="file" style="display: none" ref="image"
									   accept="application/pdf, image/*, text/plain, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, video/*"
									   @change="onFilePicked">
							</v-flex>
							<v-container fluid>
								<v-card>
									<v-card-text>List of Softwares
									</v-card-text>
									<v-layout row wrap >
										<v-flex xs12 sm4 md4 v-for="soft in listSoftware" :key="soft.id">
											<v-checkbox v-model="cloneProductConfiguration.softwares" :label=soft.name :value=soft></v-checkbox>
										</v-flex>
									</v-layout>
								</v-card>
							</v-container>
							<v-container fluid>
							<v-card>
								<v-card-text>List of Modules
								</v-card-text>
								<v-layout row wrap>
									<v-flex xs12 sm4 md4 v-for="module in listOfModules" :key="module.id">
										<v-checkbox v-model="cloneProductConfiguration.modules" :label=module.parametrization :value=module></v-checkbox>
									</v-flex>
								</v-layout>
							</v-card>
					</v-container>
						</v-layout>
					</v-container>
				</v-card-text>
				<v-card-actions v-if="showEditUser">
					<v-spacer></v-spacer>
					<v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
					<v-btn color="blue darken-1" flat @click="save">Save</v-btn>
				</v-card-actions>
				<v-card-actions v-if="showSelectUser">
					<v-spacer></v-spacer>
					<v-btn color="blue darken-1" flat @click="close">Cancel</v-btn>
					<v-btn color="blue darken-1" flat @click="cloneConfig">Clone</v-btn>
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
				contractDialog: false,
				listSoftware: [],
				listOfModules:[],
				arraySoftState: [
					'ACTIVE',
					'INACTIVE',
					'SUSPENDED',
				],
				arrayHardware:[
					'Cloud',
				'Server',
				'Laptop',
				],
				headers: [
					{
						text: "Client Name",
						sortable: true,
						value: "username",
					},
					{
						text: "Description",
						sortable: true,
					},
					{
						text: "Product Config Name",
						sortable: true,
					},
					{
						text: "License",
						sortable: true,
					},
					{
						text: "Software state",
						sortable: true,
					},
					{
						text: "Contract Info",
						sortable: true,
					},
					{
						text: "Hardware required",
						sortable: true,
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
						text: "Modules",
						sortable: true,
					},
					{
						text: "Clone",
						sortable: true,
					},
					{
						text: "Actions"
					}
				],
				products: [],
				baseCode: "",
				baseCodeURL: "",
				baseCodeFile: "",
				dialog: false,
				showSelectUser: false,
				showEditUser: false,
				dialogSoftware: false,
				dialogModules: false,
				dialogArtifacts: false,
				editedProductConfiguration: {
					username: "",
					license: "",
					description: "",
					stateOfSoftware: "",
					hardwareRequired: "",
					name: "",
					softwares: [],
					modules: [],

				},
				cloneProductConfiguration: {
					username: "",
					license: "",
					description: "",
					stateOfSoftware: "",
					hardwareRequired: "",
					name: "",
					softwares: [],
					modules: [],
					contractData: {
						contractInfo: '',
					}
				},
				clients: [],
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
			this.fetchClients();
			this.fetchSoftware();
			this.fetchModules();
		},
		methods: {
			editQa(qa) {
				console.log("Prod Config",qa);
				Object.assign(this.editedQa.prodConfig, qa);
				this.dialogQuestion = true;
				console.log("Edited QA",this.editedQa);

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
			showSoftware: function () {
				this.dialogSoftware = true;
			},
			showModules: function () {
				this.dialogModules = true;
			},
			fetchClients: function () {
				this.$axios.get('api/clients', {
					auth: {
						username: this.$axios.defaults.auth.username,
						password: this.$axios.defaults.auth.password
					}
				})
					.then(response => {
						this.clients = response.data;
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
						console.log(" Softwares",response);
						this.listSoftware = response.data;
					})
					.catch(error => {
						console.log(error);
					});
			},
			fetchModules: function () {
				this.$axios.get('api/modules', {
					auth: {
						username: this.$axios.defaults.auth.username,
						password: this.$axios.defaults.auth.password
					}
				})
					.then(response => {
						this.listOfModules = response.data;
						console.log("MODULES",this.listOfModules);
					})
					.catch(error => {
						console.log(error.message);
					});
			},
			cloneConfig: function () {
				let clone = {
					username: this.cloneProductConfiguration.username,
					contract: {
						contractInfo: this.cloneProductConfiguration.contractData.contractInfo,
						idClient: this.cloneProductConfiguration.username,
					},
					license: this.cloneProductConfiguration.license,
					stateOfSoftware: this.cloneProductConfiguration.stateOfSoftware,
					hardwareRequired: this.cloneProductConfiguration.hardwareRequired,
					name: this.cloneProductConfiguration.name,
					description: this.cloneProductConfiguration.description,
					softwares: this.cloneProductConfiguration.softwares,
					modules: this.cloneProductConfiguration.modules
				};
				this.contractDialog = false;
				this.$axios
					.post("api/productConfig", clone)
					.then(response => {
						var formData = new FormData();
						formData.append("baseCode", this.baseCodeFile);
						console.log("resposta", response.data);
						this.$axios
							.post(
								"api/productConfig/supportMaterials/" + this.baseCodeFile.name + "/" + response.data.id,
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
								this.fetchProducts();
								this.fetchClients();
								this.fetchSoftware();
								this.fetchModules();
								this.close();
								console.log(response, "upload");
								console.log("Product configuration created");
							})
							.catch(error => {
								console.log(error);
							});
					})
					.catch(error => {
						console.log("aqui");
						console.log(error);
					});
			},
			fetchProducts: function () {
				this.$axios.get('api/productConfig',{
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
			deleteProduct: function (product) {
				console.log(product);
				this.$axios.delete('api/productConfig/' + product.id).then(response => {
					this.fetchProducts();
					this.$axios.get('api/users/getEmail/'+product.name,{
									auth: {
										username: this.$axios.defaults.auth.username,
										password: this.$axios.defaults.auth.password
									}
								})
							.then(response => {
								console.log("Resposta email"+response.data)
								this.$axios.$post('http://localhost:3000/email', { emailClient: response.data,
																					action : 'Remove', 
																					nameProdConf: product.name ,
																					username : product.name,
																					type : 'Product configuration'});						
							})
								.catch(error => {
								console.log(error);
							});

					console.log("product deleted")
				}).catch(error => {
					console.log(error);
				});
			},
			editProduct(product) {
				Object.assign(this.editedProductConfiguration, product);
				this.dialog = true;
				this.showSelectUser = false;
				this.showEditUser = true;
				console.log(this.editedProductConfiguration);
			},
			cloneProduct(product) {
				Object.assign(this.cloneProductConfiguration, product);
				this.dialog = true;
				this.showSelectUser = true;
				this.showEditUser = false;
				console.log(this.cloneProductConfiguration);
			},
			close() {
				this.dialogSoftware = false;
				this.dialog = false;
				this.showSelectUser = false;
				this.dialogModules = false;
				this.dialogQuestion = false;
				setTimeout(() => {
					this.editedItem = Object.assign({}, this.defaultItem);
					this.editedIndex = -1
				}, 300)
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
			saveQA(){
				this.$axios.post('api/qa/create', this.editedQa)
				.then(response => {
					console.log("Question asked");
				})
				.catch(error=>{
					console.log("Question failed");
				})
			},
			save() {
				this.$axios.put('api/productConfig/', this.editedProductConfiguration).then(response => {
					var formData = new FormData();
						formData.append("baseCode", this.baseCodeFile);
						var vm= response.data;
						console.log("resposta", response.data);
						this.$axios
							.post(
								"api/productConfig/supportMaterials/" + this.baseCodeFile.name + "/" + this.editedProductConfiguration.id,
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
								this.fetchProducts();
								this.fetchClients();
								this.fetchSoftware();
								this.fetchModules();
								this.close();
								console.log("Product edited")
							})
							.catch(error => {
								console.log(error);
							});

							this.$axios.get('api/users/getEmail/'+response.data.username,{
									auth: {
										username: this.$axios.defaults.auth.username,
										password: this.$axios.defaults.auth.password
									}
								})
							.then(response => {
								console.log("Resposta email"+response.data)
								this.$axios.$post('http://localhost:3000/email', { emailClient: response.data,
																					action : 'Update', 
																					nameProdConf: vm.name ,
																					username : vm.username,
																					type : 'Product configuration'});						
							})
								.catch(error => {
								console.log(error);
							});

							
				}).catch(error => {
					console.log(error.message);
				})
			}
		}
	};
</script>
