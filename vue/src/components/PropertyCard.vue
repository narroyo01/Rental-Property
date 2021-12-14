<template>
  <v-card width="374" height="610" @click="nav()">
    <v-img height="250" :src="property.imageUrl"></v-img>

    <v-card-title>{{ property.address }}</v-card-title>

    <div class="ms-4 me-2 overflow-y-auto" style="height:240px">
      <p>{{ property.description }} </p>
    </div>

    <p class="ms-4 mt-4">Rent: ${{ property.rent }}</p>
  </v-card>
</template>

<script>
export default {
  name: "PropertyCard",
  props: ["property"],
  methods: {
    nav() {
      this.$router.push("/property/" + this.property.propertyId);
    },
  },
};
</script>

<style scoped>
</style>