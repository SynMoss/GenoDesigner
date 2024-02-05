<template>
  <el-form ref="genInfoForm" :model="info" :rules="rules" label-width="150px">
    <el-row>
      <el-col span="12">
        <el-form-item prop="tplCategory">
          <span slot="label">Generate Template</span>
          <el-select v-model="info.tplCategory" @change="tplSelectChange">
            <el-option label="Single Table (CRUD)" value="crud" />
            <el-option label="Tree Table (CRUD)" value="tree" />
            <el-option label="Master-Slave Table (CRUD)" value="sub" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col span="12">
        <el-form-item prop="packageName">
          <span slot="label">
            Generate Package Path
            <el-tooltip
              content="Under which java package will it be generated, e.g., com.hmzhkj.system"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.packageName" />
        </el-form-item>
      </el-col>

      <el-col span="12">
        <el-form-item prop="moduleName">
          <span slot="label">
            Generate Module Name
            <el-tooltip
              content="Can be understood as the sub-system name, e.g., system"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.moduleName" />
        </el-form-item>
      </el-col>

      <el-col span="12">
        <el-form-item prop="businessName">
          <span slot="label">
            Generate Business Name
            <el-tooltip
              content="Can be understood as the functional English name, e.g., user"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.businessName" />
        </el-form-item>
      </el-col>

      <el-col span="12">
        <el-form-item prop="functionName">
          <span slot="label">
            Generate Function Name
            <el-tooltip
              content="Used for class description, e.g., user"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.functionName" />
        </el-form-item>
      </el-col>

      <el-col span="12">
        <el-form-item>
          <span slot="label">
            Parent Menu
            <el-tooltip
              content="Assigned to the specified menu, e.g., System Management"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <treeselect
            append-to-body="true"
            v-model="info.parentMenuId"
            :options="menus"
            :normalizer="normalizer"
            show-count="true"
            placeholder="Please select system menu"
          />
        </el-form-item>
      </el-col>

      <el-col span="12">
        <el-form-item prop="genType">
          <span slot="label">
            Generation Code Method
            <el-tooltip
              content="Default is zip compressed download. Can also customize the generation path."
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-radio v-model="info.genType" label="0">zip compressed</el-radio>
          <el-radio v-model="info.genType" label="1">Custom Path</el-radio>
        </el-form-item>
      </el-col>

      <el-col span="24" v-if="info.genType == '1'">
        <el-form-item prop="genPath">
          <span slot="label">
            Custom Path
            <el-tooltip
              content="Enter the absolute disk path. If not filled, it will be generated under the current Web project."
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.genPath">
            <el-dropdown slot="append">
              <el-button type="primary">
                Quick Select Recent Paths
                <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="info.genPath = '/'"
                  >Reset to Default Generation Base Path</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row v-show="info.tplCategory == 'tree'">
      <h4 class="form-header">Other Information</h4>
      <el-col span="12">
        <el-form-item>
          <span slot="label">
            Tree Encoding Field
            <el-tooltip
              content="The encoding field name for tree display, e.g., dept_id"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeCode" placeholder="Please select">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Tree Parent Code Field
            <el-tooltip
              content="The field name of the parent code displayed in the tree, such as parent_Id"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeParentCode" placeholder="Please select">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Tree Name Field
            <el-tooltip
              content="The field name of the display name for tree nodes, such as dept_name"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeName" placeholder="Please select">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row v-show="info.tplCategory == 'sub'">
      <h4 class="form-header">Related Information</h4>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Related Subtable Name
            <el-tooltip
              content="The name of the related subtable, such as sys_user"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select
            v-model="info.subTableName"
            placeholder="Please select"
            @change="subSelectChange"
          >
            <el-option
              v-for="(table, index) in tables"
              :key="index"
              :label="table.tableName + '：' + table.tableComment"
              :value="table.tableName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            The foreign key name of the subtable association
            <el-tooltip
              content="The foreign key name of the subtable association, such as user_id"
              placement="top"
            >
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.subTableFkName" placeholder="Please select">
            <el-option
              v-for="(column, index) in subColumns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  components: { Treeselect },
  props: {
    info: {
      type: Object,
      default: null,
    },
    tables: {
      type: Array,
      default: null,
    },
    menus: {
      type: Array,
      default: [],
    },
  },
  data() {
    return {
      subColumns: [],
      rules: {
        tplCategory: [
          {
            required: true,
            message: "Please select the template to generate.",
            trigger: "blur",
          },
        ],
        packageName: [
          {
            required: true,
            message: "Please enter the package path to generate.",
            trigger: "blur",
          },
        ],
        moduleName: [
          {
            required: true,
            message: "Please enter the module name to generate.",
            trigger: "blur",
          },
        ],
        businessName: [
          {
            required: true,
            message: "Please enter the business name to generate.",
            trigger: "blur",
          },
        ],
        functionName: [
          {
            required: true,
            message: "Please enter the function name to generate.",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {},
  watch: {
    "info.subTableName": function (val) {
      this.setSubTableColumns(val);
    },
  },
  methods: {
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children,
      };
    },

    subSelectChange(value) {
      this.info.subTableFkName = "";
    },

    tplSelectChange(value) {
      if (value !== "sub") {
        this.info.subTableName = "";
        this.info.subTableFkName = "";
      }
    },

    setSubTableColumns(value) {
      for (var item in this.tables) {
        const name = this.tables[item].tableName;
        if (value === name) {
          this.subColumns = this.tables[item].columns;
          break;
        }
      }
    },
  },
};
</script>
