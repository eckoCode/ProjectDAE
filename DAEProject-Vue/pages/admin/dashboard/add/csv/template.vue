<template>
	<v-form>
		<v-btn @click="clear">clear</v-btn>
		<v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
			<v-text-field label="Import CSV" @click="pickFile" v-model="baseCode"
						  prepend-icon="attach_file"></v-text-field>
			<input type="file" style="display: none" ref="image" @change="onFilePicked">
		</v-flex>
		<v-btn @click="uploadCsv">Upload</v-btn>
	</v-form>
</template>

<script>
	export default {
		data: () => ({
			valid: true,
			baseCode: "",
			baseCodeURL: "",
			baseCodeFile: "",
		}),
		methods: {
			uploadCsv() {
				var formData = new FormData();
				formData.append("baseCode", this.baseCodeFile);
				this.$axios
					.post("api/templates/csv", formData, {
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
	}
</script>

<style scoped>

</style>
