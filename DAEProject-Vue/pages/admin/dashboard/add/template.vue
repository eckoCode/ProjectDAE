<template>
	<v-app app>
		<v-form ref="form" v-model="valid" lazy-validation>
			<v-text-field v-model="template.name" :rules="descriptionRules" :counter="100" label="Name" required></v-text-field>
			<v-text-field v-model="template.description" :rules="descriptionRules" :counter="100" label="Description" required></v-text-field>
			<v-select :items="listOfHardware" v-model="template.hardware" item-text="hardware" label="Hardware"></v-select>
			<v-text-field v-model="template.license" :rules="descriptionRules" :counter="100" label="License" required></v-text-field>
			<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
				<v-text-field label="Import PDF" @click="pickFile" v-model="baseCode" prepend-icon="attach_file"></v-text-field>
				<input type="file" style="display: none" ref="image" accept="application/pdf" @change="onFilePicked">
			</v-flex>
			<v-container fluid>
				<v-card>
					<v-card-text>List of Softwares
					</v-card-text>
					<v-layout row wrap>
						<v-flex xs12 sm4 md4 v-for="soft in listSoftware" :key="soft.id">
							<v-checkbox v-model="template.software" :label=soft.name :value=soft></v-checkbox>
						</v-flex>
					</v-layout>
				</v-card>
			</v-container>
			<v-btn :disabled="!valid" @click="submit">submit</v-btn>
		</v-form>
	</v-app>
</template>

<script>
	export default {
		data: () => ({
			valid: true,
			baseCode: "",
			baseCodeURL: "",
			baseCodeFile: "",
			descriptionRules: [
				v => !!v || 'Field is required'
			],
			listOfHardware: [
				'Cloud',
				'Server',
				'Laptop',
			],
			template: {
				name: '',
				description: '',
				hardware: '',
				license: '',
				software: [],
			},
			listSoftware: null,
		}),
		methods: {
			uploadCsv() {
				var formData = new FormData();
				formData.append("baseCode", this.baseCodeFile)
				this.$axios
					.post(
						"api/templates/csv",
						formData, {
							auth: {
								username: this.$axios.defaults.auth.username,
								password: this.$axios.defaults.auth.password
							}
						}, {
							headers: {
								"Content-Type": "multipart/form-data"
							}
						}
					)
					.then(response => {
						console.log(response);
					})
					.catch(error => {
						console.log(error);
					});
			},
			submit() {
				if (this.$refs.form.validate()) {
					// Native form submission is not yet supported
					let template = {
						name: this.template.name,
						description: this.template.description,
						hardware: this.template.hardware,
						license: this.template.license,
						software: this.template.software,
					};
					console.log("template", template);
					this.$axios.post('api/templates/create', template).then(response => {
						console.log("Template Created");
						var formData = new FormData();
						formData.append("baseCode", this.baseCodeFile);
						console.log("resposta", response.data);
						this.$axios
							.post(
								"api/templates/supportMaterials/" + this.baseCodeFile.name + "/" + response.data.id,
								formData, {
									auth: {
										username: this.$axios.defaults.auth.username,
										password: this.$axios.defaults.auth.password
									}
								}, {
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

					}).catch(error => {
						console.log(error.message);
					});
				}
			},
			clear() {
				this.$refs.form.reset()
			},
			fetchSoftware: function() {
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
		},
		created() {
			this.fetchSoftware();
		}
	}
</script>


<style scoped>
	
</style>
