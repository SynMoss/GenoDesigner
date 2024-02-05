<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="MenuName" prop="menuName">
        <el-input
          v-model="queryParams.menuName"
          placeholder="Please Enter MenuName"
          clearable
          @keyup.enter.native="handleQuery"
          @blur="queryParams.menuName = $event.target.value.trim()"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select v-model="queryParams.status" placeholder="Menu Status" clearable @keyup.enter.native="handleQuery"
          @clear="handleQuery">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery" style="font-size: 14px;padding: 10px 20px;">Search</el-button>
        <el-button  @click="resetQuery" style="font-size: 14px;padding: 10px 20px;">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
        style="font-size: 14px;padding: 10px 20px;"
          type="primary"
          @click="handleAdd"
          v-hasPermi="['system:menu:add']"
        >New</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
        style="font-size: 14px;padding: 10px 20px;"
          @click="toggleExpandAll"
        >Unfold/Fold</el-button>
      </el-col>
    </el-row>
    <div class="table-f">
    <vxe-table
      resizable
      v-loading="loading"
      max-height="100%"
      border="inner"
      ref="SysMenuList"
      :tree-config="{transform: false, rowField: 'menuId', parentField: 'parentId'}"
      :data="menuList"
      @toggle-tree-expand="toggleExpandChangeEvent">
      <vxe-column field="menuName" title="MenuName" tree-node min-width="100px" fixed="left"></vxe-column>
      <vxe-column field="icon" title="Icon" min-width="50px">
        <template slot-scope="scope">
          <svg-icon v-if="scope.row.icon" :icon-class="scope.row.icon" />
        </template>
      </vxe-column>
      <vxe-column field="orderNum" title="OrderNum" min-width="50px"  show-overflow="tooltip"></vxe-column>
      <vxe-column field="perms" title="Perms" min-width="100px"  show-overflow="tooltip"></vxe-column>
      <vxe-column field="component" title="Component" min-width="200px"  show-overflow="tooltip"></vxe-column>
      <vxe-column field="status" title="Status" min-width="80px"  show-overflow="tooltip">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </vxe-column>
      <vxe-column field="createTime" title="CreateTime" min-width="150px"  show-overflow="tooltip"></vxe-column>
      <vxe-column field="j" title="Operate" min-width="150px"  fixed="right">
        <template slot-scope="scope">
          <a @click="handleUpdate(scope.row)" type="text" size="small" class="table-opera" v-hasPermi="['system:user:edit']"
            >Edit</a
          >
          <a @click="handleAdd(scope.row)" type="text" size="small" class="table-opera" v-hasPermi="['system:menu:add']"
            >New</a
          >
          <a @click="handleDelete(scope.row)" type="text" size="small" class="table-opera" v-hasPermi="['system:user:remove']"
            >Delete</a
          >
        </template>
      </vxe-column>
      </vxe-table>
    </div>
         <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form label-position="top" size="small" v-loading="getLoading" ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="ParentId" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="Choose ParentId"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="MenuType" prop="menuType">
              <el-radio-group v-model="form.menuType">
                <el-radio label="M">Directory</el-radio>
                <el-radio label="C">Menu</el-radio>
                <el-radio label="F">button</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="form.menuType != 'F'">
            <el-form-item label="Menu Icon" prop="icon">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected" />
                <el-input slot="reference" v-model="form.icon" placeholder="Click The Select Icon" readonly>
                  <svg-icon
                    v-if="form.icon"
                    slot="prefix"
                    :icon-class="form.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="MenuName" prop="menuName">
              <el-input v-model="form.menuName" placeholder="Please Enter MenuName" @blur="form.menuName = $event.target.value.trim()"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Display OrderNum" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="isFrame">
              <span slot="label">
                <el-tooltip content="If You Select Backlink, The Routing Address Must Start With 'http(s)://'" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Whether It's A Backlink
              </span>
              <el-radio-group v-model="form.isFrame">
                <el-radio :label="1">Yes</el-radio>
                <el-radio :label="0">No</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="path">
              <span slot="label">
                <el-tooltip content="The Routing Address Of The Access, For Example, 'User', And If The Public Network Address Needs To Be Accessed By Internal Link, It Starts With 'http(s)://'" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Routing Address
              </span>
              <el-input v-model="form.path" placeholder="Please Enter Routing Address" @blur="form.path = $event.target.value.trim()"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="The Path Of The Accessed Component, For Example, 'system/user/index', Is In The 'views' Directory By Default" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Component Path
              </span>
              <el-input v-model="form.component" placeholder="Please Component Path" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'M'">
            <el-form-item prop="perms">
              <el-input v-model="form.perms" placeholder="Please Enter Permission ID" maxlength="100" @blur="form.perms = $event.target.value.trim()"/>
              <span slot="label">
                <el-tooltip content="Permission Characters Defined In The Controller, e.g. @PreAuthorize('@ss.hasPermi('system:user:list')')" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Permission ID
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="query">
              <el-input v-model="form.query" placeholder="Please Enter Routing Parameters" maxlength="255" @blur="form.query = $event.target.value.trim()"/>
              <span slot="label">
                <el-tooltip content='The Default Passing Parameters Of The Access Route, For Example:`{"id": 1, "name": "ry"}`' placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Routing Parameters
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="isCache">
              <span slot="label">
                <el-tooltip content="If You Select Yes, It Will Be Cached By 'keep-alive', And The 'name' And Address Of The Matching Component Must Be The Same" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Whether Or Not To Cache
              </span>
              <el-radio-group v-model="form.isCache">
                <el-radio :label="1">Cache</el-radio>
                <el-radio :label="0">No Cache</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="form.menuType == 'C'">
            <el-form-item label="Superior Breadcrumbs">
              <treeselect
                v-model="form.breadcrumbParentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="Superior Breadcrumbs"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="visible">
              <span slot="label">
                <el-tooltip content="Select Hide And The Route Will Not Appear In The Sidebar, But It Will Still Be Accessible" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Display Status
              </span>
              <el-radio-group v-model="form.visible">
                <el-radio
                  v-for="dict in dict.type.sys_show_hide"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="status">
              <span slot="label">
                <el-tooltip content="If You Select Deactivate, The Route Will Not Appear In The Sidebar And Will Not Be Accessible" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Menu Status
              </span>
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :disabled="subLoading" :loading="subLoading">OK</el-button>
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMenu, getMenu, delMenu, addMenu, updateMenu } from "@/api/system/menu";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";

export default {
  name: "Menu",
  dicts: ['sys_show_hide', 'sys_normal_disable'],
  components: { Treeselect, IconSelect },
  data() {
    return {
      getLoading: false,
      subLoading: false,

      loading: true,

      showSearch: true,

      menuList: [],

      menuOptions: [],

      title: "",

      open: false,

      isExpandAll: false,

      refreshTable: true,

      queryParams: {
        menuName: '',
        visible: undefined
      },

      form: { menuName:'', path:'', component:'', perms:'', query:''},

      rules: {
        menuName: [
          { required: true, message: "MenuName Cannot Be Empty", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "MenuOrderNum Cannot Be Empty", trigger: ["blur", "change"] }
        ],
        path: [
          { required: true, message: "Routing Address Cannot Be Empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    selected(name) {
      this.form.icon = name;
    },

    getList() {
      this.loading = true;
      listMenu(this.queryParams).then(response => {
        this.menuList = this.handleTree(response.data, "menuId");
        this.loading = false;
      });
    },

    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      };
    },

    getTreeselect() {
      listMenu().then(response => {
        this.menuOptions = [];
        const menu = { menuId: 0, menuName: 'Main Category', children: [] };
        menu.children = this.handleTree(response.data, "menuId");
        this.menuOptions.push(menu);
      });
    },

    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        menuId: undefined,
        parentId: '0',
        menuName: undefined,
        icon: undefined,
        menuType: "M",
        orderNum: undefined,
        isFrame: 0,
        isCache: 1,
        visible: "0",
        status: "0"
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.queryParams.menuName = this.queryParams.menuName.trim()
      this.getList();
    },

    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.menuId) {
        this.form.parentId = row.menuId;
      } else {
        this.form.parentId = '0';
      }
      this.open = true;
      this.title = "Add Menu";
    },

    toggleExpandAll() {
      if(!this.isExpandAll){
        this.$refs.SysMenuList.setAllTreeExpand(true)
      }else{
        this.$refs.SysMenuList.clearTreeExpand()
      }
      this.isExpandAll = !this.isExpandAll;
    },

    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      this.getLoading = true
      this.open = true;
      getMenu(row.menuId).then(response => {
        this.form = response.data;
        this.title = "Edit Menu";
      }).finally(()=>{
        this.getLoading = false
      });
    },

    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.subLoading = true
          if (this.form.menuId != undefined) {
            updateMenu(this.form).then(response => {
              this.$modal.msgSuccess("Edit Success");
              this.open = false;
              this.getList();
            }).finally(()=>{

              location.reload()
              this.subLoading = false
            });
          } else {
            addMenu(this.form).then(response => {
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

    handleDelete(row) {
      this.$modal.confirm('Check Whether To Confirm That The Deletion MenuName Is Set To"' + row.menuName + '"Data Itmes？').then(function() {
        return delMenu(row.menuId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Success");
      }).catch(() => {});
    },
    toggleExpandChangeEvent ({ row, expanded }) {
      const $table = this.$refs.SysMenuList
      console.log('Node Expansion Event', expanded, 'Get The Parent Node：', $table.getParentRow(row))
    },
    getTreeExpansionEvent () {
      const $table = this.$refs.SysMenuList
      const treeExpandRecords = $table.getTreeExpandRecords()
      VXETable.modal.alert(treeExpandRecords.length)
    }
  }
};
</script>

<style scoped>
  .table-f{
    height: calc(100vh - 230px);
  }
</style>>

