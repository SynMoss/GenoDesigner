<template>
  <div class="app-container" style="background-color:#fff;height:100%;">
    <h4 class="form-header h4">Basic Information</h4>
    <el-form ref="form" size="small" :model="form" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="NickName" prop="nickName">
            <el-input v-model="form.nickName" disabled @blur="form.nickName = $event.target.value.trim()"/>
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="UserName" prop="userName">
            <el-input v-model="form.userName" disabled @blur="form.userName = $event.target.value.trim()"/>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <h4 class="form-header h4">Role information</h4>
    <div class="table-f">
    <vxe-table
    border="inner"
    height="100%"
      ref="xTable1"
      :data="roles"
      auto-resize
      v-loading="loading"
      :row-config="{isHover: true}"
      show-overflow
      @radio-change="handleSelectionChange"
      :seq-config="{startIndex:(this.tablePage.currentPage-1) * this.tablePage.pageSize}">
      <vxe-column type="radio" min-width="60px" fixed="left"></vxe-column>
      <vxe-column type="seq" title="Number" min-width="50px" fixed="left" show-overflow="tooltip"></vxe-column>
      <vxe-column field="roleId" title="Role ID" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="roleName" title="Role Name" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="roleKey" title="Role Key" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="createTime" title="Create Time" min-width="100px" show-overflow="tooltip"></vxe-column>
    </vxe-table>
  </div>
    <div class="pager-flex">
      <div class="pager-flex-total"></div>
      <vxe-pager
        class-name="pager-self"
        background
        size="small"
        :loading="loading"
        :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize"
        :total="tablePage.totalResult"
        :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'Sizes', 'FullJump']"
        @page-change="pageChangeEvent">
      </vxe-pager>
    </div>
    <div style="display:flex;justify-content: center;">
      <el-form label-width="100px">
      <el-form-item
      >
        <el-button
          type="primary"
          @click="submitForm()"
          :disabled="subLoading"
          :loading="subLoading"
          >Submit</el-button
        >
        <el-button @click="close()" :disabled="subLoading" :loading="subLoading"
          >Return</el-button
        >
      </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getAuthRole, updateAuthRole } from '@/api/system/user'
import { table } from "@/mixins/table";
export default {
  name: 'AuthRole',
  mixins: [table],
  data() {
    return {
      subLoading: false,
      loading: true,
      total: 0,
      pageNum: 1,
      pageSize: 10,
      roleIds: [],
      roles: [],
      form: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100
      }
    }
  },
  created() {
    const userId = this.$route.params && this.$route.params.userId
    if (userId) {
      this.queryParams.pageNum = this.tablePage.currentPage
      this.queryParams.pageSize = this.tablePage.pageSize
      this.loading = true
      getAuthRole(userId).then((response) => {
        this.form = response.user
        this.roles = response.roles
        this.tablePage.totalResult = response.total
        this.$nextTick(() => {
          this.roles.forEach((row) => {
            if (row.flag) {
              this.clickRow(row)
            }
          })
        })
        this.loading = false
      })
    }
  },
  methods: {
    clickRow(row) {
      //this.$refs.xTable1.toggleCheckboxRow(row)
      this.$refs.xTable1.setRadioRow(row)
    },
    handleSelectionChange(selection) {
      const records = this.$refs.xTable1.getRadioRecord()
      let arr=[records.roleId]
      this.roleIds=arr
    },
    getRowKey(row) {
      return row.roleId
    },
    submitForm() {
      this.subLoading = true
      const userId = this.form.userId
      const roleIds = this.roleIds.join(',')
      updateAuthRole({ userId: userId, roleIds: roleIds })
        .then((response) => {
          this.$modal.msgSuccess('Authorization granted.')
          this.close()
        })
        .finally(() => {
          this.subLoading = false
        })
    },
    close() {
      const obj = { path: '/system/user' }
      this.$tab.closeOpenPage(obj)
    },
    selectAllEvent ({ checked }) {
      const records = this.$refs.xTable1.getCheckboxRecords()
    },
    selectChangeEvent ({ checked }) {
      const records = this.$refs.xTable1.getCheckboxRecords()
    },
    getSelectEvent () {
      let selectRecords = this.$refs.xTable1.getCheckboxRecords()
      VXETable.modal.alert(selectRecords.length)
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getList()
    },
  }
}
</script>
<style lang="scss" scoped>
.table-f{
  height: calc(68vh - 180px);
}
</style>
