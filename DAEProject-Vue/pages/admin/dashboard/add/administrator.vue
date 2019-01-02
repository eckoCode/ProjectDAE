<template>
    <v-form ref="form" lazy-validation>
        <v-text-field v-model="name" :rules="nameRules" :counter="10" label="Name" required></v-text-field>
        <v-overflow-btn v-model="roleUser" :items="role" tag="Role"></v-overflow-btn>
        <v-text-field v-model="email" :rules="emailRules" label="E-mail" required></v-text-field>
        <v-text-field v-model="username" :rules="nameRules" :counter="10" label="Username" required></v-text-field>
        <v-text-field v-model="password" :type="'password'" :rules="passwordRules" :counter="10" label="Password" required></v-text-field>
    
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
                v => !!v || 'Name is required'
            ],
            email: '',
            emailRules: [
                v => !!v || 'E-mail is required',
                v => /.+@.+/.test(v) || 'E-mail must be valid'
            ],
            password: '',
            passwordRules: [
                v => !!v || 'Password is required',
                v => (v && v.length >= 6) || 'Password must be have at leas 6 characters'
            ],
            role: '',
            positionRules: [
                v => !!v || 'Position is required'
            ],
            username: '',
            role: [
                'Manager',
                'Developer',
                'Tester',
                'Janitor',
                'Front end Developer',
                'Back end Developer'
            ],
            roleUser: ''
        }),
    
    
    
    
        methods: {
            submit() {
                if (this.$refs.form.validate()) {
                    // Native form submission is not yet supported
                    let admin = {
                        username: this.username,
                        password: this.password,
                        name: this.name,
                        email: this.email,
                        role: this.roleUser,
                    };
    
                    this.$axios.post('api/admins', admin)
                        .then(response => {
                            console.log("Admin created");
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