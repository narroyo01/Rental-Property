import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import colors from 'vuetify/lib/util/colors'

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            light: {
                primary: colors.teal.darken3, // #E53935
                secondary: colors.teal.darken1, // #FFCDD2
                accent: colors.teal.accent4, // #3F51B5
                danger: colors.red.accent4
            },
        },
    },
});
