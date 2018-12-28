<template>
  <v-app id="inspire" dark>
    <v-toolbar app fixed clipped-left>
      <v-toolbar-items>
        <v-btn flat to="/admin/dashboard">DAE Project</v-btn>
        <v-btn flat>Templates</v-btn>
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
    user: false
  })
};
</script>