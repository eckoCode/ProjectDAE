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
					<td class="text-xs-left">{{ props.item.license }}</td>
					<td class="text-xs-left">{{ props.item.stateOfSoftware }}</td>
					<td class="text-xs-left">
						<v-menu offset-y>
							<v-btn
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
					<td class="text-xs-center">
						<v-btn
							small class="mr-2" @click="cloneProduct(props.item)"
							color="primary"
							dark
						>
							Clone this product
						</v-btn>
					</td>
					<td class="justify-center layout px-0">
						<v-icon small class="mr-2" @click="editProduct(props.item)">edit</v-icon>
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

		<v-dialog v-model="dialog" max-width="500px">
			<v-card>
				<v-card-title>
					<span v-if="showEditUser" class="headline">Edit product configuration</span>
					<span v-if="showSelectUser" class="headline">Clone this configuration</span>
				</v-card-title>
				<v-card-text>
					<v-container grid-list-md v-if="showEditUser">
						<v-layout wrap>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="editedProductConfiguration.username" label="Name"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="editedProductConfiguration.license"
											  label="License"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="editedProductConfiguration.stateOfSoftware"
											  label="Software state"></v-text-field>
							</v-flex
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="editedProductConfiguration.hardwareRequired"
											  label="Hardware required"></v-text-field>
							</v-flex>
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
								<v-text-field v-model="cloneProductConfiguration.license"
											  label="License"></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="cloneProductConfiguration.contractData.contractInfo"
											  label="Contract info" required></v-text-field>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-select :items="arraySoftState" label="Software State"
										  v-model="cloneProductConfiguration.stateOfSoftware"></v-select>
							</v-flex>
							<v-flex xs12 sm6 md4>
								<v-text-field v-model="cloneProductConfiguration.hardwareRequired"
											  label="Hardware required"></v-text-field>
							</v-flex>
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
				arraySoftState: [
					'ACTIVE',
					'INACTIVE',
					'SUSPENDED',
				],
				headers: [
					{
						text: "Client Name",
						sortable: true,
						value: "username",
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
						text: "Clone"
					},
					{
						text: "Actions"
					}
				],
				products: [],
				dialog: false,
				showSelectUser: false,
				showEditUser: false,
				editedProductConfiguration: {
					username: '',
					license: '',
					softwareState: '',
					hardwareRequired: '',
				},
				cloneProductConfiguration: {
					username: '',
					contractData: {
						contractInfo: '',
					},
					license: '',
					stateOfSoftware: '',
					hardwareRequired: '',
				},
				clients: [],
			};
		},
		created() {
			this.fetchProducts();
			this.fetchClients();
		},
		methods: {
			fetchClients: function () {
				this.$axios.get('api/clients', {auth:{username:this.$axios.defaults.auth.username, password:this.$axios.defaults.auth.password}})
					.then(response => {
						this.clients = response.data;
					})
					.catch(error => {
						console.log(error);
					});
			},
			cloneConfig: function (config, username) {
				let clone = {
					username: this.cloneProductConfiguration.username,
					contract: {
						contractInfo: this.cloneProductConfiguration.contract.contractInfo,
						idClient: this.cloneProductConfiguration.username,
					},
					license: this.cloneProductConfiguration.license,
					stateOfSoftware: this.cloneProductConfiguration.stateOfSoftware,
					hardwareRequired: this.cloneProductConfiguration.hardwareRequired,
				};
				this.contractDialog = false;
				console.log(clone);
				this.$axios
					.post("api/productConfig", clone)
					.then(response => {
						console.log(response, "upload");
						console.log("Product configuration created");
					})
					.catch(error => {
						console.log("aqui");
						console.log(error);
					});
			},
			fetchProducts: function () {
				this.$axios.get('api/productConfig')
					.then(response => {
						console.log(response);
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
				this.dialog = false;
				this.showSelectUser = false;
				setTimeout(() => {
					this.editedItem = Object.assign({}, this.defaultItem);
					this.editedIndex = -1
				}, 300)
			},

			save() {
				this.$axios.put('api/productConfig/', this.editedProductConfiguration).then(response => {
					this.fetchProducts();
					this.close();
					console.log("Product edited")
				}).catch(error => {
					console.log(error.message);
				})
			}
		}
	};
</script>
