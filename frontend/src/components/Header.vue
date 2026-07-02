<template>
  <div class="headerContainer">
    <div class="left">
      <ul>
        <li @click="HighlightHandler(index)" v-for="(item, index) in findAllTypeList" :key="item.tid">
          <a :class="{ active: item.isHighlight }" href="javascript:;">{{ item.tname }}</a>
        </li>
      </ul>
    </div>
    <div class="right">
      <div class="rightInput" style="margin-right: 50px;">
        <el-input v-model="keywords" placeholder="搜索最新头条"></el-input>
      </div>

      <div class="btn-dropdown">
        <div v-if="nickName" style="display: flex; justify-content: center; align-items: center;">
          <el-dropdown>
            <el-button type="primary">
              您好:{{ nickName }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handlerNews">发布新闻</el-dropdown-item>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item>浏览记录</el-dropdown-item>
                <el-dropdown-item @click="Logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
        <div v-else class="containerButton">
          <el-button size="small" style="background: #212529; color: #aea7a2" @click="toLogin">登录</el-button>
          <el-button size="small" style="background: #ffc107; color: #684802" @click="toRegister">注册</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'Header'
})
</script>

<script setup>
import { getfindAllTypes, isUserOverdue } from '../api/index'
import { ref, onMounted, getCurrentInstance, watch, onUpdated } from "vue"
import { useRoute, useRouter } from 'vue-router'
import { ArrowDown } from '@element-plus/icons-vue'
import { removeToken } from '../utils/token-utils'
import pinia from '../stores/index'
import { useUserInfoStore } from '../stores/userInfo'

const userInfoStore = useUserInfoStore(pinia)
const nickName = ref("")
const { Bus } = getCurrentInstance().appContext.config.globalProperties
const router = useRouter()
const route = useRoute()
const keywords = ref(route.query.keyWords || "")
const findAllTypeList = ref([])

const syncHighlight = (currentType) => {
  findAllTypeList.value.forEach((item) => {
    item.isHighlight = item.tid === currentType
  })
}

watch(keywords, (newVal) => {
  if (route.name === 'HeadlineNews') {
    Bus.emit('keyword', newVal)
  }
})

watch(() => route.query.keyWords, (newVal) => {
  keywords.value = newVal || ""
})

watch(() => route.query.type, (newVal) => {
  syncHighlight(Number(newVal || 0))
})

const toLogin = () => {
  router.push({ name: "Login" })
}

const toRegister = () => {
  router.push({ name: "Register" })
}

const getList = async () => {
  let result = await getfindAllTypes()
  result.forEach((item) => {
    item.isHighlight = false
  })
  result.unshift({
    isHighlight: false,
    tid: 0,
    tname: "微头条"
  })
  findAllTypeList.value = result
  syncHighlight(Number(route.query.type || 0))
}

onUpdated(() => {
  nickName.value = userInfoStore.nickName
})

onMounted(() => {
  getList()
})

const HighlightHandler = (index) => {
  const currentType = findAllTypeList.value[index].tid
  syncHighlight(currentType)
  Bus.emit('tid', currentType)
}

const Logout = () => {
  removeToken()
  userInfoStore.initUserInfo()
  nickName.value = ""
  router.go({ name: "HeadlineNews" })
}

const handlerNews = async () => {
  await isUserOverdue()
  router.push({ name: "addOrModifyNews" })
}
</script>

<style>
.el-dropdown {
  vertical-align: top;
  width: 100px;
}

.el-dropdown + .el-dropdown {
  margin-left: 15px;
}

.el-icon-arrow-down {
  font-size: 12px;
}
</style>

<style lang="less" scoped>
.headerContainer {
  width: 100%;
  height: 60px;
  background: #212529;
  display: flex;
  justify-content: space-around;
  .left {
    ul {
      display: flex;
      li {
        list-style: none;
        margin-left: 20px;
        a:-webkit-any-link {
          text-decoration: none;
          color: #59646b;
          &.active {
            color: #c0adab;
          }
        }
      }
    }
  }
  .right {
    .containerButton {
      display: flex;
      align-items: center;
    }
    line-height: 60px;
    display: flex;
    flex-wrap: nowrap;
    .rightInput {
      display: flex;
      align-items: center;
      :deep(.el-input__inner) {
        height: 30px;
        width: 150px;
      }
    }
    .btn-dropdown {
      display: flex;
      align-items: center;
    }
    :deep(.el-button) {
      margin: 0 0 0 10px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}

.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
