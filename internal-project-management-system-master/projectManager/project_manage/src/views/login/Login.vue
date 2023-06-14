<template>
  <div class="login">
    <login-form></login-form>
  </div>
</template>

<script>
// @ is an alias to /src
 import LoginForm from '@/views/login/LoginForm.vue'

export default {
  name: 'login',
  components:{
    LoginForm
  }
}
</script>
<style scoped>
.login{
  position: relative;
  width: 100%;
  height: 100vh;
  background: url(../../assets/images/login_bg.png) no-repeat;
  background-size: 100% 100%;
  background-attachment: fixed;
}
</style>

