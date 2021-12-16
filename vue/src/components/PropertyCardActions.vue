<template>
  <v-card
    width="374"
    :height="isAuthorized ? 700 : 610"
    style="cursor: pointer"
  >
    <v-img @click="nav()" height="250" :src="property.imageUrl"></v-img>
    <span @click="nav()">
      <v-card-title>{{ property.address }}</v-card-title>

      <p class="ms-4" v-if="isAuthorized">
        {{ property.available ? "Available" : "Occupied" }}
      </p>

      <div class="ms-4 me-2 overflow-y-auto" style="height: 240px">
        <p class="ms-4">{{ property.description }}</p>
      </div>

      <p class="ms-4 mt-4">Rent: ${{ property.rent }}</p>
    </span>
    <v-btn
      color="secondary"
      class="ms-4 mb-4"
      v-if="isAuthorized"
      @click="navToUpdate(property.propertyId)"
      >Update</v-btn
    >
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
    nav() {
      this.$router.push("/property/" + this.property.propertyId);
    },
  },
};
</script>

<style scoped>
</style>