<template>
  <div class="container">
    <div class="listItem">
      <!-- 每一项头条列表 -->
      <div class="containerItem" v-for="item in pageData" :key="item.hid">
        <div>
          <span class="text">{{ item.title }}</span>
        </div>
        <div class="detail">
          <span>{{ item.type == 1 ? "新闻":item.type == 2 ? "体育": item.type == 3 ? "娱乐": item.type == 4 ? "科技" : "其他" }}</span>
          <span>{{item.pageViews}}浏览</span>
          <span>{{item.pastHours}}小时前</span>
        </div>
        <div>
          <el-button @click="toDetail(item.hid)" size="small"
            style="background: #198754; margin-left: 15px; color: #bbd3dc">查看全文</el-button>
          <el-popconfirm v-if="item.publisher == type" @confirm="handlerDelete(item.hid)" :title="`您确定要删除${item.title}吗?`">
            <template #reference>
              <el-button    size="small" style="background: #dc3545; color: #bbd3dc">删除</el-button>
            </template>
          </el-popconfirm>

          <el-button @click="Modify(item.hid)" v-if="item.publisher == type"  size="small" style="background: #212529; color: #bbd3dc">修改</el-button>
        </div>
      </div>

      <!-- 分页器 -->
      <div style="margin-top: 20px">
        <el-pagination
          v-model:current-page="findNewsPageInfo.pageNum"
          v-model:page-size="findNewsPageInfo.pageSize"
          @size-change="getPageList"
          @current-change="getPageList"
          :page-sizes="[5,7,10]"
          background
          layout="prev, pager, next , ->, sizes, total"
          :total="totalSize" />
      </div>
    </div>
  </div>
</template>

<script >
import { getfindNewsPageInfo , removeByHid } from "../../api/index"
 import { defineComponent } from 'vue'
  export default  defineComponent({
    name:'HeadlineNews'
  })
</script>
<script  setup>
import { ref, onMounted, getCurrentInstance, watch } from "vue"
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import pinia from '../../stores/index';
import { useUserInfoStore } from '../../stores/userInfo'
const  { Bus } = getCurrentInstance().appContext.config.globalProperties
const userInfoStore = useUserInfoStore(pinia)
const router = useRouter()
const route = useRoute()
const type = userInfoStore.uid

const getQueryNumber = (value, fallback) => {
  const number = Number(value)
  return Number.isFinite(number) && number > 0 ? number : fallback
}

const findNewsPageInfo = ref(
  {
    keyWords: route.query.keyWords || "",
    type: getQueryNumber(route.query.type, 0),
    pageNum: getQueryNumber(route.query.pageNum, 1),
    pageSize: getQueryNumber(route.query.pageSize, 5),
  }
)
const totalSize = ref(0)
const pageData = ref([{
  hid: null,
  pageViews: null,
  pastHours: null,
  publisher: null,
  title: "",
  type: null
}])

const syncListQuery = () => {
  router.replace({
    name: 'HeadlineNews',
    query: {
      keyWords: findNewsPageInfo.value.keyWords || undefined,
      type: findNewsPageInfo.value.type || undefined,
      pageNum: findNewsPageInfo.value.pageNum,
      pageSize: findNewsPageInfo.value.pageSize,
    }
  })
}

Bus.on('keyword', (keywords) => {
  findNewsPageInfo.value.keyWords = keywords
  findNewsPageInfo.value.pageNum = 1
})

Bus.on('tid', (type) => {
  findNewsPageInfo.value.type = type
  findNewsPageInfo.value.pageNum = 1
})

watch(() => findNewsPageInfo.value, () => {
  syncListQuery()
  getPageList()
}, {
  deep: true,
})

const getPageList = async () => {
  let result = await getfindNewsPageInfo(findNewsPageInfo.value)
  pageData.value = result.pageInfo.pageData
  findNewsPageInfo.value.pageNum = result.pageInfo.pageNum
  findNewsPageInfo.value.pageSize = result.pageInfo.pageSize
  totalSize.value = +result.pageInfo.totalSize
}

onMounted(() => {
  syncListQuery()
  getPageList()
})

const toDetail = (hid) => {
  router.push({
    name: "Detail",
    query: {
      hid,
      keyWords: findNewsPageInfo.value.keyWords || undefined,
      type: findNewsPageInfo.value.type || undefined,
      pageNum: findNewsPageInfo.value.pageNum,
      pageSize: findNewsPageInfo.value.pageSize,
    }
  })
}

const handlerDelete = async (id) => {
  await removeByHid(id)
  ElMessage.success('删除成功!')
  getPageList()
}

const Modify = (hid) => {
  router.push({ name: "addOrModifyNews", query: { hid } })
}
</script>

<style lang="less" scoped>
.container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;

  .listItem {
    .containerItem {
      margin-top: 20px;
      border-radius: 10px;
      border: 2px solid #ebebeb;
      width: 600px;
      height: 120px;

      div {
        margin-top: 10px;
      }

      .text {
        margin-left: 15px;
        color: #353a3f;
      }

      .detail {
        span {
          margin-left: 15px;
          color: #8b778a;
          font-size: 14px;
        }
      }
    }
  }
}
</style>
