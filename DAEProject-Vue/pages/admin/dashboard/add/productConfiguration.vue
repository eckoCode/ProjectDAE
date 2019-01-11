<template>
	<v-form ref="form" v-model="valid" lazy-validation>
		<v-select :items="templates" v-model="template"  @input="autoCompleteTemplate" item-text="id" label="Choose a template"></v-select>
		<v-select :items="clients" v-model="client" item-text="username" label="Client"></v-select>
		<v-text-field v-model="prodConfig.hardwareRequired" :rules="hardwareRequiredRules" :counter="10" label="Hardware required"
					  required></v-text-field>
		<v-select :items="arraySoftState" label="Software State" v-model="prodConfig.stateOfSoftware"></v-select>
		<v-text-field v-model="prodConfig.license" :rules="licensesRules" label="License" required></v-text-field>
		<div class="text-xs-center">
			<v-dialog v-model="contractDialog" width="500">
				<v-btn slot="activator" color="red lighten-2" dark>Set a Contract</v-btn>
				<v-card>
					<v-card-title
						class="headline grey lighten-2"
						primary-title
					>
						Contract Info
					</v-card-title>
					<v-divider></v-divider>
					<v-card-text>
						<v-text-field v-model="contractData.contractInfo" :rules="contractDataRules" :counter="10"
									  label="Contract info" required></v-text-field>
						<v-text-field v-model="client" disabled :rules="contractDataRules" :counter="10"
									  label="Client id" required></v-text-field>
					</v-card-text>
					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn
							color="primary"
							flat
							@click="contractDialog = false"
						>
							save
						</v-btn>
					</v-card-actions>
				</v-card>
			</v-dialog>
		</div>
		<v-text-field v-model="prodConfig.name" :rules="nameRules" :counter="10" label="Name"
					  required></v-text-field>
		<v-text-field v-model="prodConfig.description" :rules="descriptionRules" :counter="10" label="Description"
					  required></v-text-field>

		<!-- 
			MODULES
			-->
		<v-container fluid>
				<v-card>
					<v-card-text>List of Modules
					</v-card-text>
					<v-layout row wrap>
						<v-flex xs12 sm4 md4 v-for="module in listOfModules" :key="module.id">
							<v-checkbox v-model="prodConfig.modules" :label=module.parametrization :value=module></v-checkbox>
						</v-flex>
					</v-layout>
				</v-card>
		</v-container>
		
		<!-- 
			SOFTWARES
			-->
		<v-container fluid>
				<v-card>
					<v-card-text>List of Software
					</v-card-text>
					<v-layout row wrap>
						<v-flex xs12 sm4 md4 v-for="soft in listSoftware" :key="soft.id">
							<v-checkbox v-model="prodConfig.software" :label=soft.name :value=soft></v-checkbox>
						</v-flex>
					</v-layout>
				</v-card>
		</v-container>
		
		<!-- 
			SUPPORTMATERIALS
			-->
		<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
				<v-text-field label="Select File" @click="pickFile" v-model="baseCode"
							  prepend-icon="attach_file"></v-text-field>
				<input type="file" style="display: none" ref="image"
					   accept="application/pdf"
					   @change="onFilePicked">
		</v-flex>
		<v-dialog v-model="dialog" max-width="290"></v-dialog>
		<v-btn :disabled="!valid" @click="submit">submit</v-btn>
		<v-btn @click="clear">clear</v-btn>
	</v-form>
</template>

<script>
	export default {
		created() {
			this.fetchClients();
		},
		data: () => ({
			arraySoftState: [
				'ACTIVE',
				'INACTIVE',
				'SUSPENDED',
			],
			valid: true,
			nameRules: [v => !!v || "Name is required"],
			descriptionRules: [v => !!v || "Description is required"],
			licensesRules: [],
			softwareStateRules: [v => !!v || "State is required"],
			hardwareRequiredRules: [v => !!v || "Hardware Requirements are required"],
			contractDataRules: [v => !!v || "Contract Data is required"],
			template: {
				id:'',
				name: '',
				description: '',
				hardware: '',
				license: '',
				software: [],
			},
			prodConfig: {
				license: "",
				description: "",
				stateOfSoftware: "",
				hardwareRequired: "",
				client: {},
				name: "",
				software: [],
				modules: [],

			},
			contractData: {
				"contractInfo": "",
				"idClient": "",
			},
			item: "",
			dialog: false,
			contractDialog: false,
			baseCode: "",
			baseCodeURL: "",
			baseCodeFile: "",
			client: {},
			clients: [],
			templates: [],
			listSoftware: null,
			listOfModules: null
		}),

		methods: {
			submit() {
				if (this.$refs.form.validate()) {
					this.contractData.idClient = this.client;
					// Native form submission is not yet supported
					let productConfiguration = {
						username: this.client,
						hardwareRequired: this.prodConfig.hardwareRequired,
						stateOfSoftware: this.prodConfig.stateOfSoftware,
						license: this.prodConfig.license,
						//id
						contract: this.contractData,
						name: this.prodConfig.name,
						description: this.prodConfig.description,
						modules: this.prodConfig.modules,
						softwares: this.prodConfig.software,	
					};
					this.$axios.post("api/productConfig", productConfiguration).then(response => {
						var formData = new FormData();
						formData.append("baseCode", this.baseCodeFile);
						var vm= response.data;
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
								console.log("upload");
							})
							.catch(error => {
								console.log(error);
							});
							console.log("Product configuration created");
							this.$axios.get('api/users/getEmail/'+response.data.username,{
									auth: {
										username: this.$axios.defaults.auth.username,
										password: this.$axios.defaults.auth.password
									}
								})
							.then(response => {
								console.log("Resposta email"+response.data)
								this.$axios.$post('http://localhost:3000/email', { emailClient: response.data,
																					action : 'Created', 
																					nameProdConf: vm.name ,
																					username : vm.username,
																					type : 'Product configuration'});						
							})
								.catch(error => {
								console.log(error);
							});

						

						})
						.catch(error => {
							console.log("aqui");
							console.log(error);
						});
				}
			},
			autoCompleteTemplate: function(event){
				this.$axios.get('api/templates/get/'+event)
					.then(response => {
						this.prodConfig.description = response.data.description;
						this.prodConfig.hardwareRequired = response.data.hardware;
						this.prodConfig.license = response.data.license;
						this.prodConfig.software = response.data.software;
						this.supportMaterials = response.data.supportMaterials;
					})
					.catch(error => {
						console.log(error);
					});
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
			fetchClients: function () {
				this.$axios.get('api/clients')
					.then(response => {
						this.clients = response.data;
					})
					.catch(error => {
						console.log(error);
					});
			},
			fetchTemplates: function () {
				this.$axios.get('api/templates')
					.then(response => {
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
						console.log(response);
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
			}
		},
		created() {
			this.fetchSoftware();
			this.fetchClients();
			this.fetchModules();
			this.fetchTemplates();
		}
	};
</script>
