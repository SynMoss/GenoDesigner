<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="RoleName" prop="roleName">
        <el-input
          v-model="queryParams.roleName"
          placeholder="Please Enter RoleName"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          @blur="queryParams.roleName = $event.target.value.trim()"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="RoleKey" prop="roleKey">
        <el-input
          v-model="queryParams.roleKey"
          placeholder="Please Enter RoleKey"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          @blur="queryParams.roleKey = $event.target.value.trim()"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Status"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="CreateTime">
        <el-date-picker
          clearable
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="Start Date"
          end-placeholder="End Date"

        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"  size="mini" style="font-size: 14px; padding: 10px 20px;"  @click="handleQuery">Search</el-button>
        <el-button  size="mini" style="font-size: 14px; padding: 10px 20px;"  @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
        style="font-size: 14px;padding: 10px 20px;"
          type="primary"
          @click="handleAdd"
          v-hasPermi="['system:role:add']"
        >New</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
        style="font-size: 14px;padding: 10px 20px;"
          type="danger"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:role:remove']"
        >Delete</el-button>
      </el-col>
           </el-row>
    <div class="table-f">
    <vxe-table
      ref="SysRoleList"
      :data="roleList"
      v-loading="loading"
      height="100%"
      border="inner"
      :row-config="{isHover: true}"
      @checkbox-all="selectChangeEvent"
      show-overflow
      @checkbox-change="selectChangeEvent"
      :seq-config="{startIndex:(this.tablePage.currentPage-1) * this.tablePage.pageSize}"
      :checkbox-config="{checkMethod: checkBoxConfig}"
      >
      <vxe-column type="checkbox" min-width="50px" fixed="left"></vxe-column>
      <vxe-column type="seq" title="Seq" min-width="50px" fixed="left"></vxe-column>
      <vxe-column field="roleName" title="RoleName" min-width="100px" fixed="left" show-overflow="tooltip"></vxe-column>
      <vxe-column field="roleKey" title="RoleKey" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="roleSort" title="RoleSort" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="status" title="Status"  min-width="80px" show-overflow="tooltip">
        <template slot-scope="scope" v-if="scope.row.userId !== 1">
            <vxe-switch
              v-model="scope.row.status"
              open-value="0"
              close-value="1"
              @change="handleStatusChange(scope.row)"
              v-if="unchangeable.indexOf(scope.row.roleKey) <= -1"
            ></vxe-switch>
            <span v-else>Enabled</span>
        </template>
      </vxe-column>
      <vxe-column field="createTime" title="CreateTime" min-width="160px" show-overflow="tooltip" ></vxe-column>
      <vxe-column field="j" title="操作" min-width="150px" show-overflow="tooltip" fixed="right">
      <template slot-scope="scope">
        <a @click="handleUpdate(scope.row)" type="text"
          size="small" class="table-opera" v-hasPermi="['system:user:edit']"
          v-if="unchangeable.indexOf(scope.row.roleKey) <= -1"
          >Edit</a>
        <a v-else type="text" size="small" class="table-opera unusable" v-hasPermi="['system:user:edit']"
          >Edit</a>
        <a @click="handleDelete1(scope.row)" type="text" size="small"
          class="table-opera" v-hasPermi="['system:user:remove']"
          v-if="unchangeable.indexOf(scope.row.roleKey) <= -1"
          >Delete</a>
        <a type="text" size="small"
          class="table-opera unusable" v-hasPermi="['system:user:remove']"
          v-else
          >Delete</a>
        <a @click="handleCommand('handleAuthUser',scope.row)"
          type="text" size="small" class="table-opera" v-hasPermi="['system:user:edit']"
          v-if="scope.row.roleKey != 'admin'"
        >Assign Users</a>
        <a
          type="text" size="small" class="table-opera unusable" v-hasPermi="['system:user:edit']"
          v-else
        >Assign Users</a>
               </template>
    </vxe-column>
    </vxe-table>
  </div>
    <div class="pager-flex">
      <div class="pager-flex-total" v-text="totalShow()"></div>
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

         <el-dialog :close-on-click-modal="false"  :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form label-position="top" size="small" v-loading="getLoading" ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="RoleName" prop="roleName">
          <el-input v-model="form.roleName" placeholder="Please Enter RoleName" ref="roleName" @blur="form.roleName = $event.target.value.trim()"/>
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="Controller RoleKey，如：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            RoleKey
          </span>
          <el-input v-model="form.roleKey" placeholder="Please Enter RoleKey" ref="roleKey"  @blur="form.roleKey = $event.target.value.trim()"/>
        </el-form-item>
        <el-form-item label="RoleSort" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="Status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Menu Permissions">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">Unfold/Fold</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">Select All/None Of Them</el-checkbox>
                     <el-tree
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            ref="menu"
            node-key="id"
            :check-strictly="!form.menuCheckStrictly"
            empty-text="loading..."
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
        <el-form-item label="Remark">
          <el-input
          v-model="form.remark"
          type="textarea"
          placeholder="Please Enter Content"
          @blur="form.remark = $event.target.value.trim()"
          resize="none"
          maxlength="256"
          show-word-limit
          :rows="6"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :disabled="subLoading" :loading="subLoading">OK</el-button>
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading">Cancel</el-button>
      </div>
    </el-dialog>

         <el-dialog :close-on-click-modal="false"  :title="title" :visible.sync="openDataScope" width="500px" append-to-body>
      <el-form  label-position="top" size="small"  v-loading="getLoading" :model="form" label-width="80px">
        <el-form-item label="RoleName">
          <el-input v-model="form.roleName"  @blur="form.roleName = $event.target.value.trim()" :disabled="true" />
        </el-form-item>
        <el-form-item label="RoleKey">
          <el-input v-model="form.roleKey"  @blur="form.roleKey = $event.target.value.trim()" :disabled="true" />
        </el-form-item>
        <el-form-item label="Scope Of Permissions">
          <el-select v-model="form.dataScope" @change="dataScopeSelectChange">
            <el-option
              v-for="item in dataScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Data Permissions" v-show="form.dataScope == 2">
          <el-checkbox v-model="deptExpand" @change="handleCheckedTreeExpand($event, 'dept')">Unfold/Fold</el-checkbox>
          <el-checkbox v-model="deptNodeAll" @change="handleCheckedTreeNodeAll($event, 'dept')">Select All/None Of Them</el-checkbox>
          <el-checkbox v-model="form.deptCheckStrictly" @change="handleCheckedTreeConnect($event, 'dept')">Father-son Linkage</el-checkbox>
          <el-tree
            class="tree-border"
            :data="deptOptions"
            show-checkbox
            default-expand-all
            ref="dept"
            node-key="id"
            :check-strictly="!form.deptCheckStrictly"
            empty-text="loading..."
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDataScope" :disabled="subLoading" :loading="subLoading">Cancel</el-button>
        <el-button type="primary" @click="submitDataScope" :disabled="subLoading" :loading="subLoading">OK</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="true"
      title="Delete OK"
      :visible.sync="confirmRemoveVisible"
      width="520px"
      append-to-body
    >
      <div class="diaglog-remove-icon"><i class="el-icon-warning "></i></div>
      <div class="dialog-p">{{removeTip}} </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="confirmRemoveVisible = false" :disabled="subLoading" :loading="subLoading"
          >Cancel</el-button
        >
        <el-button
          type="primary"
          @click="remove"
          :disabled="subLoading"
          :loading="subLoading"
          >OK</el-button
        >

      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRole, getRole, delRole, addRole, updateRole, dataScope, changeRoleStatus, deptTreeSelect } from "@/api/system/role";
import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";

export default {
  name: "Role",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      confirmRemoveVisible: false,
      removeTip: '',
      getLoading: false,
      subLoading: false,
    
      loading: true,
      
      ids: [],
    
      rolenames:[],
     
      single: true,
    
      multiple: true,
      
      showSearch: true,
      
      total: 0,
      
      roleList: [],
     
      title: "",
      
      open: false,
     
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      
      dateRange: [],
      
      unchangeable:['admin', 'geneBlackAdmin', 'geneBlackGeneral'],
      
      dataScopeOptions: [
        {
          value: "1",
          label: "Full Data Permissions"
        },
        {
          value: "2",
          label: "Customize Data Permissions"
        },
        {
          value: "3",
          label: "Data Permissions Of The Department"
        },
        {
          value: "4",
          label: "This Department And The Following Data Permissions"
        },
        {
          value: "5",
          label: "Only My Data Permissions"
        }
      ],

      menuOptions: [],
      
      deptOptions: [],
      
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: '',
        roleKey: '',
        status: undefined
      },
      
      form: {roleName:'', roleKey:'', remark:''},
      defaultProps: {
        children: "children",
        label: "label"
      },
      
      rules: {
        roleName: [
          { required: true, message: "RoleName Cannot Be Empty", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "RoleKey Cannot Be Empty", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "RoleSort Cannot Be Empty", trigger: ["blur", "change"] }
        ]
      },
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
   
    getList() {
      this.queryParams.pageNum = this.tablePage.currentPage
      this.queryParams.pageSize = this.tablePage.pageSize
      this.loading = true;
      listRole(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.roleList = response.rows;
          this.tablePage.totalResult = response.total
          this.loading = false;
        }
      );
    },
   
    getMenuTreeselect() {
      menuTreeselect().then(response => {
        this.menuOptions = response.data;
      });
    },
    
    getMenuAllCheckedKeys() {
      
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
   
    getDeptAllCheckedKeys() {
      
      let checkedKeys = this.$refs.dept.getCheckedKeys();
      
      let halfCheckedKeys = this.$refs.dept.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
   
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuOptions = response.menus;
        return response;
      });
    },
    
    getDeptTree(roleId) {
      return deptTreeSelect(roleId).then(response => {
        this.deptOptions = response.depts;
        return response;
      });
    },
    
    handleStatusChange(row) {
      let text = row.status === "0" ? "Enable" : "Deactivated";
      this.$modal.confirm('Confirm It"' + text + '""' + row.roleName + '"Role？').then(function() {
        return changeRoleStatus(row.roleId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "Success");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    
    cancel() {
      this.open = false;
      this.reset();
    },

    checkBoxConfig({row}){
      return this.unchangeable.indexOf(row.roleKey) <= -1
    },
    
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.menuExpand = false,
      this.menuNodeAll = false,
      this.deptExpand = true,
      this.deptNodeAll = false,
      this.form = {
        roleId: undefined,
        roleName: '',
        roleKey: '',
        roleSort: 0,
        status: "0",
        menuIds: [],
        deptIds: [],
        menuCheckStrictly: true,
        deptCheckStrictly: true,
        remark: undefined
      };
      this.resetForm("form");
    },
    
    handleQuery() {
      this.queryParams.roleName = this.queryParams.roleName.trim()
      this.queryParams.roleKey = this.queryParams.roleKey.trim()
      this.tablePage.currentPage = 1
      this.getList();
    },
    
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
   
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
   
    handleCommand(command, row) {
      switch (command) {
        case "handleDataScope":
          this.handleDataScope(row);
          break;
        case "handleAuthUser":
          this.handleAuthUser(row);
          break;
        default:
          break;
      }
    },
    dataChange() {
      if(this.dateRange == null){
      this.getList();
      }
    },
    
    handleCheckedTreeExpand(value, type) {
      if (type == 'menu') {
        let treeList = this.menuOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
        }
      } else if (type == 'dept') {
        let treeList = this.deptOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'menu') {
        this.$refs.menu.setCheckedNodes(value ? this.menuOptions: []);
      } else if (type == 'dept') {
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions: []);
      }
    },
  
    handleCheckedTreeConnect(value, type) {
      if (type == 'menu') {
        this.form.menuCheckStrictly = value ? true: false;
      } else if (type == 'dept') {
        this.form.deptCheckStrictly = value ? true: false;
      }
    },
    
    handleAdd() {
      this.reset();
      this.getMenuTreeselect();
      this.open = true;
      this.title = "Add Role";
    },
    
    handleUpdate(row) {
      this.reset();
      this.getLoading = true
      this.open = true;
      const roleId = row.roleId
      const roleMenu = this.getRoleMenuTreeselect(roleId);
      getRole(roleId).then(response => {
        this.form = response.data;
        this.$nextTick(() => {
          roleMenu.then(res => {
            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
                this.$nextTick(()=>{
                    this.$refs.menu.setChecked(v, true ,false);
                })
            })
          });
        });
        this.title = "Edit Role";
      }).finally(()=>{
        this.getLoading = false
      });
    },
    
    dataScopeSelectChange(value) {
      if(value !== '2') {
        this.$refs.dept.setCheckedKeys([]);
      }
    },
    
    handleDataScope(row) {
      this.reset();
      const deptTreeSelect = this.getDeptTree(row.roleId);
      getRole(row.roleId).then(response => {
        this.form = response.data;
        this.openDataScope = true;
        this.$nextTick(() => {
          deptTreeSelect.then(res => {
            this.$refs.dept.setCheckedKeys(res.checkedKeys);
          });
        });
        this.title = "Assign Data Permissions";
      });
    },
    
    handleAuthUser: function(row) {
      const roleId = row.roleId;
      this.$router.push({path: '/system/role-auth/user/'+ roleId, query: {roleId: roleId, roleName: row.roleName}});
    },
    
    submitForm: function() {
      if(this.form.roleKey==undefined||this.form.roleName==''){
        this.$refs.roleKey.focus();
      }
      if(this.form.roleName==undefined||this.form.roleName==''){
        this.$refs.roleName.focus();
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.subLoading = true
          if (this.form.roleId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            updateRole(this.form).then(response => {
              this.$modal.msgSuccess("Edit Success");
              this.open = false;
              this.getList();
            }).finally(()=>{
              this.subLoading = false
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            addRole(this.form).then(response => {
              this.$modal.msgSuccess("Add Success");
              this.open = false;
              this.getList();
            }).finally(()=>{
              this.subLoading = false
            });
          }
        }
      });
    },
   
    submitDataScope: function() {
      if (this.form.roleId != undefined) {
        this.subLoading = true
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then(response => {
          this.$modal.msgSuccess("Edit Success");
          this.openDataScope = false;
          this.getList();
        }).finally(()=>{
              this.subLoading = false
            });
      }
    },
    
    handleDelete(row) {
      this.confirmRemoveVisible = true
      if(row.roleId){
        this.ids=row.roleId
      }
      this.removeTip = 'Confirm Or Not DeleteRoleName"' + this.rolenames + '"Data Items？'
    },
    remove(){
      this.subLoading = true
      delRole(this.ids).then(res=>{
        this.getList()
        this.$modal.msgSuccess('Delete Success')
      }).finally(() => {
                this.subLoading = false
                this.confirmRemoveVisible = false
              })
    },
    handleDelete1(row) {
      this.confirmRemoveVisible = true
      if(row.roleId){
        this.ids=row.roleId
      }
      this.removeTip = 'Confirm Or Not DeleteRoleName"' +row.roleName + '"Data Items？'
    },
    
    handleExport() {
      this.queryParams.ids=this.ids,
      this.download('system/role/export', {
        ...this.queryParams
      }, `role_${new Date().getTime()}.xlsx`)
    },
   
    selectChangeEvent ({ checked }) {
      const records = this.$refs.SysRoleList.getCheckboxRecords()
      let arr=[]
      let arr1 = []
      for(let item of records){
        arr.push(item.roleId)
        arr1.push(item.roleName)
      }
      this.ids=arr
      this.rolenames=arr1
     
      if (records && records.length) {
          this.multiple = false;
      } else{
          this.multiple = true;
      }
    },
    getSelectEvent () {
      let selectRecords = this.$refs.SysRoleList.getCheckboxRecords()
      VXETable.modal.alert(selectRecords.length)
    },
    
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getList()
    },
   
    totalShow() {
      let total = this.tablePage.totalResult;
      let str = "Total ";
      let query = this.queryParams;
      if (
        query.projectName ||
        (query.createStaffNoList && query.createStaffNoList.length != 0)
      ) {
        str = "Searched ";
      }
      return str + total + " Items";
    },
  }
};
</script>
<style lang="scss" scoped>
  .table-f{
    height: calc(100vh - 312px);
  }
  .app-container {
    margin: 20px 20px 0 20px;
    padding: 20px;
    background-color: #fff;
    max-height: calc(100vh - 80px);
  }
  .unusable {
    color: #cccccc;
    &:hover {
      cursor:not-allowed
    }
  }

  ::v-deep{
  .is--disabled.vxe-table--filter-option .vxe-checkbox--icon, .is--disabled.vxe-export--panel-column-option .vxe-checkbox--icon, .vxe-table--render-default .is--disabled.vxe-cell--checkbox .vxe-checkbox--icon, .is--disabled.vxe-custom--option .vxe-checkbox--icon, .is--disabled.vxe-checkbox .vxe-checkbox--icon{
    background-color: #dcdfe6 !important
  }
}
</style>
