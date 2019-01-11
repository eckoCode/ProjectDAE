<template>
	<v-app id="inspire" app dark>
		<v-toolbar app fixed clipped-left>
			<v-toolbar-items>
				<div v-if="this.$axios.defaults.auth != undefined">
					<v-btn v-if ="this.type=='Administrator' " flat to="/admin/dashboard">DAE Project</v-btn>
				</div>
				<v-btn flat to="/templates">Templates</v-btn>
			</v-toolbar-items>
			<v-spacer></v-spacer>
			<v-toolbar-items v-if="user === false">
				<v-btn flat to="/login">Login</v-btn>
			</v-toolbar-items>
			<v-toolbar-items v-else>
				<v-btn flat>Logout</v-btn>
			</v-toolbar-items>
		</v-toolbar>
		<nuxt/>
	</v-app>
</template>

<script>
	export default {
		data: () => ({
			drawer: null,
			user: false,
			type: '',
		}),
		created() {
			let vm = this;
			if (this.$axios.defaults.auth != undefined){
				this.$axios.$get('/api/users/' + this.$axios.defaults.auth.username).then(function (response) {
					if (response == "Administrator") {
						vm.type = response;
												this.type = "Administrator"
					} else if (response == "Client") {
						vm.type = response;
						this.type = "Client"
					} else {
						vm.$router.replace("login");
					}
					console.log(this.type)
				})
					.catch(function (error) {
						console.log(error);
					});
			}else{
				vm.$router.replace("login");
			}

		}
	};
</script>
