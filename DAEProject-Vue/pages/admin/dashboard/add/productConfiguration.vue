<template>
    <v-form ref="form" v-model="valid" lazy-validation>
        <v-overflow-btn  :items="clients" item-text="username" label="Client username" target="#dropdown-example"></v-overflow-btn>
        <v-text-field v-model="name" :rules="nameRules" :counter="10" label="Name" required></v-text-field>
        <v-text-field v-model="baseVersion" :rules="baseVersionRules" :counter="10" label="Base version" required></v-text-field>
        <v-text-field v-model="license" :rules="licensesRules" label="License" required></v-text-field>
        <v-text-field v-model="parameterization" :rules="parametrizationRules" :counter="10" label="Parameterization" required></v-text-field>
        <v-text-field v-model="supportMaterials" :rules="supportMaterialsRules" :counter="10" label="Support materials" required></v-text-field>
        <v-text-field v-model="description" :rules="descriptionRules" :counter="10" label="Description" required></v-text-field>
        <v-text-field v-model="softwareState" :rules="softwareStateRules" :counter="10" label="Software state" required></v-text-field>
        <v-text-field v-model="hardwareRequired" :rules="hardwareRequiredRules" :counter="10" label="Hardware required" required></v-text-field>
        <v-text-field v-model="contractData" :rules="contractDataRules" :counter="10" label="Contract data" required></v-text-field>
        <v-flex xs12 class="text-xs-center text-sm-center text-md-center text-lg-center">
            <v-text-field label="Select File" @click="pickFile" v-model="baseCode" prepend-icon="attach_file"></v-text-field>
            <input type="file" style="display: none" ref="image" accept="file/*" @change="onFilePicked">
        </v-flex>
        <v-dialog v-model="dialog" max-width="290"></v-dialog>
    
        <v-btn :disabled="!valid" @click="submit">submit</v-btn>
        <v-btn @click="clear">clear</v-btn>
    </v-form>
</template>

<script>
    export default {
        created()
        {
            this.fetchClients();
        },
        data: () => ({
            valid: true,
            nameRules: [v => !!v || "Name is required"],
            baseVersionRules: [v => !!v || "Name is required"],
            licensesRules: [],
    
            parametrizationRules: [v => !!v || "Name is required"],
    
            supportMaterialsRules: [v => !!v || "Name is required"],
    
            descriptionRules: [v => !!v || "Name is required"],
    
            baseCodeRules: [v => !!v || "Name is required"],
    
            softwareStateRules: [v => !!v || "Name is required"],
            hardwareRequiredRules: [v => !!v || "Name is required"],
            contractDataRules: [v => !!v || "Name is required"],
    
            name: "",
            baseVersion: "",
            license: "",
            parameterization: "",
            supportMaterials: "",
            description: "",
            baseCode: "",
            softwareState: "",
            hardwareRequired: "",
            contractData: "",
            item:"",
            dialog: false,
            baseCode: "",
            baseCodeURL: "",
            baseCodeFile: "",
            clients: []
        }),
    
        methods: {
            submit() {
                if (this.$refs.form.validate()) {
                    // Native form submission is not yet supported
                    let productConfiguration = {
                        name: this.name,
                        baseVersion: this.baseVersion,
                        //license: this.license,
                        parameterization: this.parameterization,
                        baseCode: this.baseCode,
                        supportMaterials: this.supportMaterials,
                        description: this.description,
                        softwareState: this.softwareState,
                        hardwareRequired: this.hardwareRequired,
                        contractData: this.contractData,
                        username: this.clients || "asdasda"
                    };
    
                    this.$axios
                        .post("api/products", productConfiguration)
                        .then(response => {
                            this.$axios.post('api/products/addBaseCode', this.baseCodeFile, {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }).then(response => {
                                console.log("upload");
                                console.log("Product configuration created");
                            }).catch(error => {
                                console.log("ali")
                                console.log(error.data);
                            })
                        })
                        .catch(error => {
                            console.log("aqui")
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
            fetchClients: function() {
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