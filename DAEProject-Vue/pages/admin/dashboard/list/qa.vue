<template>
    <v-app>
    <v-card>
      <v-card-title>Q & A
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search by company name" single-line hide-details></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="qa" :search="search">
        <template slot="items" slot-scope="props">
                          <td>{{ props.item.id }}</td>
                          <td class="justify-left">{{ props.item.prodConfig.description }}</td>
                          <td class="text-xs-left">{{ props.item.question }}</td>
                          <td class="text-xs-left">{{ props.item.answer }}</td>
                        <td><v-icon small class="mr-2" @click="answerQ(props.item)">edit</v-icon></td>
                      
        </template>
        <v-alert
          slot="no-results"
          :value="true"
          color="error"
          icon="warning"
        >Your search for "{{ search }}" found no results.</v-alert>
      </v-data-table>
    </v-card>
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">Answer</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field v-model="editQaWithAnswer.answer" label="Your Answer*" required></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" flat @click="dialog = false">Close</v-btn>
          <v-btn color="blue darken-1" flat  @click="save">Save</v-btn>
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
        headers: [
            {
                text: "Id",
                align: "left",
                sortable: true
            },
            {
                text: "Product",
                sortable: true,
            },
            {
                text: "Question",
                sortable: true,
            },
            {
                text: "Answer",
                sortable: true,
            },
            {
                text: "Reply"
            }
        ],
        clients: [],
        qa: [],
        dialog: false,
        answer: "",
        editQaWithAnswer: {
            id: "",
            answer: "",
            question: "",
            prodConfig: {}
          }
      };
    },
    created() {
      this.fetchQA();
    },
    methods: {
      fetchQA: function() {
          this.$axios.get('api/qa')
          .then(response => {
            this.qa = response.data;
            console.log(this.qa);
          })
          .catch(error => {
            console.log(error);
          });
      },
      answerQ(qa) {
        Object.assign(this.editQaWithAnswer, qa);
        this.dialog = true;
        console.log(this.editQaWithAnswer);
      },
      save() {
          let qaWithAnswer = {
              id: this.editQaWithAnswer.id,
              answer: this.editQaWithAnswer.answer,
              question: this.editQaWithAnswer.question,
              prodConfig: this.editQaWithAnswer.prodConfig
          }
          console.log(qaWithAnswer);
          this.$axios.put('api/qa', qaWithAnswer)
          .then(response => {
              console.log('answer replied');
              this.dialog = false;
          })
          .catch(error => {
              this.dialog = false;
              console.log('answer not replied');
          })
          this.dialog = true;
      }
    }
  };
</script>

