<template>
  <div class="sidebar">
    <Row>
      <Col span="4">
      <Menu :theme="theme2" accordion width="200px" active-name="$route.path" ref="side_menu">
        <div v-for="info in menuList" :key="info.menuId">
          <Submenu v-if="info.children && info.children.length>0" :name="info.menuId">
            <template slot="title">
              <!-- <Icon  :type="info.ico" :size="10" :key="'ico' + info.menuName"/> -->
              <img class="menuicon" :src="info.ico" alt="">
              {{info.menuName}}
            </template>
            <MenuItem :name="item.url" :to="{ path: item.url}" v-for="item in info.children" :key="item.menuId" ref="menu_item">{{item.menuName}}</MenuItem>
          </Submenu>
          <MenuItem v-else active-name="$route.path" :name="info.url" :to="{ path: info.url }" ref="menu_item">
          <img class="menuicon" :src="info.ico" />
          {{info.menuName}}
          </MenuItem>
        </div>
      </Menu>
      </Col>
    </Row>
  </div>
</template>

<script>
import { currentUserInfoMethod } from 'network/login'
export default {
  name: "SideBar",
  data () {
    return {
      theme2: 'dark',
      menuList: []
    }
  },
  created () {
    // 刷新当前的用户信息
    currentUserInfoMethod(this.$store.state.token).then(data => {
      if(data.rs === 1) {
        // console.log(data)
        // console.log(this.$route);
        this.menuList = data.menuList
        // 刷新之后将当前用户信息放到store中去
        this.$store.commit('refreshCurrentUserInfo',
          {
            token: data._token_iben,
            menuList: data.menuList,
            requestPaths: data.requestPaths,
            currentUserInfo: data.currentUserInfo,
          });
        this.$nextTick(() => {
          this.openMenu();
        });
      } else {
        this.$Message.error(data.data.errorMsg)
      }
    })
  },
  methods: {
    openMenu (curName) {
      if(!curName) {
        curName = this.$route.path;
      }
      let ref = this.$refs.menu_item;
      console.log(typeof (this.$refs));
      console.log(this.$refs)
      if(!ref) {
        console.log("菜单未渲染完成")
        return;
      }
      for(let i = 0; i < ref.length; i++) {
        // MenuItem 处于选中状态就展开 Submenu
        // console.log(ref[i].name,curName)
        if("/" + ref[i].name == curName) {
          ref[i].$parent.opened = true;
          ref[i].active = true;
          if(ref[i].$parent.$parent) {
            ref[i].$parent.$parent.opened = true;
            ref[i].$parent.active = true;
          }
        }
      }
      // console.log(new Date() + "当前路由：" + curName + "---" + Math.floor(Math.random()*10))
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.$refs.side_menu.updateOpened()
      this.$refs.side_menu.updateActiveName()
    })
  }
}
</script>
<style scoped>
.sidebar {
  position: absolute;
  left: 0;
  top: 80px;
  bottom: 0;
  z-index: 9;
  height: 100%;
  background: #515a6e;
}
.menuicon {
  width: 18px;
  vertical-align: middle;
  margin-right: 4px;
  margin-top: -1px;
}
</style>
