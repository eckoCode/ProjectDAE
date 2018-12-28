<template>
    <v-app>
        <v-container pt-5 mt-5>
           
            <v-form ref="form" v-model="valid" lazy-validation>
                <v-text-field
                    v-model="username"
                    :rules="nameRules"
                    :counter="20"
                    label="Username"
                    required
                    >
                </v-text-field>
                <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'visibility_off' : 'visibility'"
                    :rules="[rules.required, rules.min]"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Password"
                    hint="At least 4 characters"
                    counter
                    @click:append="show1 = !show1"
                    >
                </v-text-field>
                <v-btn
                :disabled="!valid"
                @click="submit"
                >
                Login
                </v-btn>
            </v-form>
        </v-container>
    </v-app>
</template>
 
<script>
export default {
    data () {
    return {
        show1: false,
        password: '',
        rules: {
            required: value => !!value || 'Required.',
            min: v => v.length >= 4 || 'Min 4 characters'
        },
        valid: true,
        username: '',
        nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 20) || 'Name must be less than 10 characters'
        ],
        select: null,
        checkbox: false
    }
    },
    methods: {
        submit () {
        if (this.$refs.form.validate()) {
            let credentials = {
                username: this.username,
                password: this.password
            };
           
            this.$axios.defaults.auth = credentials;
            this.$router.push("admin/dashboard");
            /*
            this.$axios.$post('/api/login', credentials)
            .then(function (response) {
                console.log(response);
                //
 
            })
            .catch(function (error) {
                console.log(error);
            });*/
        }
 
 
        }
    }
}
</script>