<template>
	<v-form ref="form" v-model="valid" lazy-validation>
		<v-text-field v-model="name" :rules="nameRules" :counter="10" label="Name" required></v-text-field>
		<v-text-field v-model="baseCode" :rules="baseCodeRules" :counter="10" label="Base Code" required></v-text-field>
		<v-text-field v-model="baseVersion" :rules="baseVersionlRules" label="Base Version" required></v-text-field>
		<v-text-field v-model="description" :rules="descriptionRules" :counter="10" label="Description" required></v-text-field>
		<v-text-field v-model="parameterization"  :rules="parameterizationRules" :counter="10" label="Parameterization" required></v-text-field>
		<v-text-field v-model="supportMaterials"  :rules="supportMaterialsRules" :counter="10" label="Support Materials" required></v-text-field>

		<v-btn :disabled="!valid" @click="submit">submit</v-btn>
		<v-btn @click="clear">clear</v-btn>
	</v-form>
</template>

<script>
	export default {
		data: () => ({
			valid: true,
			name: '',
			nameRules: [
				v => !!v || 'Company name is required'
			],
			baseCode: '',
			baseCodeRules: [
				v => !!v || 'Address is required',
			],
			baseVersion: '',
			baseVersionlRules: [
				v => !!v || 'Base Version is required',
			],
			parameterization: '',
			parameterizationRules: [
				v => !!v || 'Parameterization is required'
			],
			description: '',
			descriptionRules: [
				v => !!v || 'Description is required'
			],
			supportMaterials: '',
			supportMaterialsRules: [
				v => !!v || 'Description is required'
			],

			description:'',
			parameterization:'',
			name:'',
			baseVersion:'',
			baseCode:'',
			supportMaterials:''

		}),
		methods: {
			submit() {
				if (this.$refs.form.validate()) {
					// Native form submission is not yet supported
					let template = {
						description: this.description,
						parameterization: this.parameterization,
						name: this.name,
						baseVersion: this.baseVersion,
						baseCode: this.baseCode,
						supportMaterials: this.supportMaterials,
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
