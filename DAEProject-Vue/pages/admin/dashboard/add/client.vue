<template>
    <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field v-model="companyName" :rules="companyNameRules" :counter="10" label="Company name" required></v-text-field>
        <v-text-field v-model="address" :rules="addressRules" :counter="10" label="Address" required></v-text-field>
        <v-text-field v-model="contactPeople" :rules="contactPeoplelRules" label="Contact people" required></v-text-field>
        <v-text-field v-model="username" :rules="usernameRules" :counter="10" label="Username" required></v-text-field>
        <v-text-field v-model="password" :type="'password'" :rules="passwordRules" :counter="10" label="Password" required></v-text-field>
    
        <v-btn :disabled="!valid" @click="submit">submit</v-btn>
        <v-btn @click="clear">clear</v-btn>
    </v-form>
</template>

<script>
    export default {
        data: () => ({
            valid: true,
            companyName: '',
            companyNameRules: [
                v => !!v || 'Company name is required'
            ],
            address: '',
            addressRules: [
                v => !!v || 'Address is required',
            ],
            contactPeople: '',
            contactPeoplelRules: [
                v => !!v || 'Contact peoplel is required',
            ],
            password: '',
            passwordRules: [
                v => !!v || 'Password is required',
                v => (v && v.length >= 6) || 'Password must be have at leas 6 characters'
            ],
            username: '',
            usernameRules: [
                v => !!v || 'Username is required'
            ],

            username:'',
            password:'',
            companyName:'',
            contactPeople:'',
            address:''
    
        }),
        methods: {
            submit() {
                if (this.$refs.form.validate()) {
                    // Native form submission is not yet supported
                    let client = {
                        username: this.username,
                        password: this.password,
                        companyName: this.companyName,
                        contactPeople: this.contactPeople,
                        address: this.address,
                    };
    
                    this.$axios.post('api/clients', client)
                        .then(response => {
                            console.log("Client created");
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            },
            clear() {
                this.$refs.form.reset()
            }
        }
    }
</script>
