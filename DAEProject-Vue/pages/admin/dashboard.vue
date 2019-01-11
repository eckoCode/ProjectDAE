<template>
  <v-app>
    <v-navigation-drawer app stateless value="true">
      <v-list>
        <v-list-group prepend-icon="account_circle" value="true">
          <v-list-tile slot="activator">
            <v-list-tile-title>Users</v-list-tile-title>
          </v-list-tile>

          <v-list-group no-action sub-group value="true">
            <v-list-tile slot="activator">
              <v-list-tile-title>Administrator</v-list-tile-title>
            </v-list-tile>

            <nuxt-link tag="span" to="/admin/dashboard/list/administrator">
              <v-list-tile slot="activator" @click>
                <v-list-tile-title class="white--text text--white-1">List</v-list-tile-title>
                <v-list-tile-action>
                  <v-icon>format_list_bulleted</v-icon>
                </v-list-tile-action>
              </v-list-tile>
            </nuxt-link>

            <nuxt-link tag="span" to="/admin/dashboard/add/administrator">
              <v-list-tile slot="activator" @click>
                <v-list-tile-title class="white--text text--white-1">Create</v-list-tile-title>
                <v-list-tile-action>
                  <v-icon>person_add</v-icon>
                </v-list-tile-action>
              </v-list-tile>
            </nuxt-link>
          </v-list-group>

          <v-list-group no-action sub-group value="true">
            <v-list-tile slot="activator">
              <v-list-tile-title>Client</v-list-tile-title>
            </v-list-tile>

            <nuxt-link tag="span" to="/admin/dashboard/list/client">
              <v-list-tile slot="activator" @click>
                <v-list-tile-title class="white--text text--white-1">List</v-list-tile-title>
                <v-list-tile-action>
                  <v-icon>format_list_bulleted</v-icon>
                </v-list-tile-action>
              </v-list-tile>
            </nuxt-link>

            <nuxt-link tag="span" to="/admin/dashboard/add/client">
              <v-list-tile slot="activator" @click>
                <v-list-tile-title class="white--text text--white-1">Create</v-list-tile-title>
                <v-list-tile-action>
                  <v-icon>person_add</v-icon>
                </v-list-tile-action>
              </v-list-tile>
            </nuxt-link>
          </v-list-group>
        </v-list-group>

        <v-list-group prepend-icon="account_circle" value="true">
          <v-list-tile slot="activator">
            <v-list-tile-title>Product Configuration</v-list-tile-title>
          </v-list-tile>

          <nuxt-link tag="span" to="/admin/dashboard/list/productConfiguration">
            <v-list-tile slot="activator" @click>
              <v-list-tile-title class="white--text text--white-1">List</v-list-tile-title>
              <v-list-tile-action>
                <v-icon>format_list_bulleted</v-icon>
              </v-list-tile-action>
            </v-list-tile>
          </nuxt-link>

          <nuxt-link tag="span" to="/admin/dashboard/add/productConfiguration">
            <v-list-tile slot="activator" @click>
              <v-list-tile-title class="white--text text--white-1">Create</v-list-tile-title>
              <v-list-tile-action>
                <v-icon>settings</v-icon>
              </v-list-tile-action>
            </v-list-tile>
          </nuxt-link>
        </v-list-group>

        <v-list-group prepend-icon="account_circle" value="true">
          <v-list-tile slot="activator">
            <v-list-tile-title>Template configuration</v-list-tile-title>
          </v-list-tile>
  
          <nuxt-link tag="span" to="/admin/dashboard/list/template">
            <v-list-tile slot="activator" @click="">
              <v-list-tile-title class="white--text text--white-1">List</v-list-tile-title>
              <v-list-tile-action>
                <v-icon>format_list_bulleted</v-icon>
              </v-list-tile-action>
            </v-list-tile>
          </nuxt-link>

          <nuxt-link tag="span" to="/admin/dashboard/add/template">
            <v-list-tile slot="activator" @click>
              <v-list-tile-title class="white--text text--white-1">Create</v-list-tile-title>
              <v-list-tile-action>
                <v-icon>receipt</v-icon>
              </v-list-tile-action>
            </v-list-tile>
          </nuxt-link>
			<nuxt-link tag="span" to="/admin/dashboard/add/csv/template">
				<v-list-tile slot="activator" @click>
					<v-list-tile-title class="white--text text--white-1">Create Template by CSV</v-list-tile-title>
					<v-list-tile-action>
						<v-icon>receipt</v-icon>
					</v-list-tile-action>
				</v-list-tile>
			</nuxt-link>
        </v-list-group>
         <nuxt-link tag="span" to="/admin/dashboard/list/qa">
            <v-list-tile slot="activator" @click>
              <v-list-tile-title class="white--text text--white-1">QA</v-list-tile-title>
              <v-list-tile-action>
                <v-icon>receipt</v-icon>
              </v-list-tile-action>
            </v-list-tile>
          </nuxt-link>
      </v-list>
    </v-navigation-drawer>
    <v-content>
      <v-container fluid fill-height>
        <v-layout justify-center align-center >
          <v-flex>
            <nuxt></nuxt>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>
<script>
export default {};
</script>

<style>
.v-navigation-drawer {
  margin-top: 64px !important;
}
</style>
