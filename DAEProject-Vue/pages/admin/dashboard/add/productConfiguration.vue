<template>
	<v-form ref="form" v-model="valid" lazy-validation>
		<v-select :items="clients" v-model="client" item-text="username" label="Client"></v-select>
		<v-text-field v-model="license" :rules="licensesRules" label="License" required></v-text-field>
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
		<v-select :items="arraySoftState" label="Software State" v-model="stateOfSoftware"></v-select>
		<v-text-field v-model="hardwareRequired" :rules="hardwareRequiredRules" :counter="10" label="Hardware required"
					  required></v-text-field>
		<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
			<v-text-field label="Select File" @click="pickFile" v-model="baseCode"
						  prepend-icon="attach_file"></v-text-field>
			<input type="file" style="display: none" ref="image" accept="file/*" @change="onFilePicked">
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
			licensesRules: [],
			softwareStateRules: [v => !!v || "Name is required"],
			hardwareRequiredRules: [v => !!v || "Name is required"],
			contractDataRules: [v => !!v || "Name is required"],
			license: "",
			description: "",
			stateOfSoftware: "",
			hardwareRequired: "",
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
			client: "",
			clients: []
		}),

		methods: {
			submit() {
				if (this.$refs.form.validate()) {
					this.contractData.idClient = this.client;
					// Native form submission is not yet supported
					let productConfiguration = {
						license: this.license,
						stateOfSoftware: this.stateOfSoftware,
						hardwareRequired: this.hardwareRequired,
						contract: this.contractData,
						username: this.client,
					};
					this.$axios
						.post("api/productConfig", productConfiguration)
						.then(response => {
							console.log(response, "upload");
							console.log("Product configuration created");
						})
						.catch(error => {
							console.log("aqui");
							console.log(error);
						});
				}
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
			}
		}
	};
</script>
