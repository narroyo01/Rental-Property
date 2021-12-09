<template>
  <v-card width="374">
    <v-img height="250" :src="property.imageUrl"></v-img>

    <v-card-title>{{ property.address }}</v-card-title>

    <p class="ms-4" v-if="isAuthorized">
      {{ property.available ? "Available" : "Occupied" }}
    </p>

    <p class="ms-4">{{ property.description }}</p>

    <p class="ms-4">Rent: ${{ property.rent }}</p>

    <v-btn
      class="ma-2"
      v-if="isAuthorized"
      @click="navToUpdate(property.propertyId)"
      >Update</v-btn
    >
    <v-btn class="ma-2" v-if="isAuthorized">Create Maintenance Request</v-btn>
  </v-card>
</template>

<script>
export default {
  name: "PropertyCardActions",
  props: ["property"],
  computed: {
    isAuthorized() {
      return (
        this.$store.state.user.authorities &&
        (this.$store.state.user.authorities[0].name === "ROLE_LANDLORD" ||
          this.$store.state.user.authorities[0].name === "ROLE_ADMIN")
      );
    },
  },
  methods: {
    navToUpdate(id) {
      this.$router.push("/update-property/" + id);
    },
  },
};
</script>

<style scoped>
</style>