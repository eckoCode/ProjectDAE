import Vue from 'vue'
import Vuetify from 'vuetify'
import colors from 'vuetify/es5/util/colors'

Vue.use(Vuetify, {
  theme: {
	  primary: '#000000',
	  secondary: '#424242',
	  accent: '#82B1FF',
	  error: '#FF5252',
	  info: '#2196F3',
	  success: '#4CAF50',
	  warning: '#FFC107'
  }
})

/*
*
*
*  primary: '#121212', // a color that is not in the material colors palette
    accent: colors.grey.darken3,
    secondary: colors.amber.darken3,
    info: colors.teal.lighten1,
    warning: colors.amber.base,
    error: colors.deepOrange.accent4,
    success: colors.green.accent3
* */
