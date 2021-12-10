<template>
  <div>
    <hello-world />
    <request-maintenance-form />
  </div>
</template>

<script>
import HelloWorld from "../components/HelloWorld";
import RequestMaintenanceForm from "../components/RequestMaintenanceForm";

export default {
  name: "Home",

  components: {
    HelloWorld,
    RequestMaintenanceForm,
  },
};
</script>
