<template>
    <v-card width="374" @click="nav()">
      <v-img height="250" :src="property.imageUrl"></v-img>

      <v-card-title>{{ property.address }}</v-card-title>

      <p class="ms-4">{{ property.description }}</p>

      <p class="ms-4">Rent: ${{ property.rent }}</p>
    </v-card>
</template>

<script>
export default {
  name: "PropertyCard",
  props: ["property"],
  methods: {
    nav() {
      this.$router.push('/property/' + this.property.propertyId)
    }
  }
};
</script>

<style scoped>
</style>