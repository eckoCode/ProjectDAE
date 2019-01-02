<template>
	<v-form ref="form" v-model="valid" lazy-validation>
		<v-text-field v-model="description" :rules="descriptionRules" :counter="100" label="Description" required></v-text-field>

		<v-btn :disabled="!valid" @click="submit">submit</v-btn>
		<v-btn @click="clear">clear</v-btn>
	</v-form>
</template>

<script>
	export default {
		data: () => ({
			valid: true,
			description: '',
			descriptionRules: [
				v => !!v || 'Description is required'
			],
			id: '',
			description:''

		}),
		methods: {
			submit() {
				if (this.$refs.form.validate()) {
					// Native form submission is not yet supported
					let template = {
						description: this.description,
						
					};

					this.$axios.post('api/templates', template).then(response => {
						this.close();
						console.log("Template Created");
					}).catch(error => {
						console.log(error.message);
					});
				}
			},
			clear() {
				this.$refs.form.reset()
			}
		}
	}
</script>


<style scoped>

</style>
