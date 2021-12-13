const path = require('path');
const fs = require("fs");
const buzEntrys = require("../multi_bundle_config/DegbugBuzEntrys.json");

let buzDebugCode = 'import {AppRegistry} from \'react-native\';\n';

buzEntrys.forEach((entryItem) => {
  let buzItemStr = fs.readFileSync(entryItem, 'utf-8');
  let buzAfter = buzItemStr.replace(/(AppRegistry,|AppRegistry ,|AppRegistry)/, '');
  let rel = path.relative('./', path.dirname(entryItem))
  rel = './'+rel+'/'
  console.log('mergeEntry', `${entryItem} => './'  rel=${rel}`);
  buzAfter = buzAfter
              .replace("from './", `from '${rel}./`)
              .replace('from "./', `from "${rel}./`)
              .replace("from '../", `from '${rel}../`)
              .replace('from "../', `from "${rel}../`)

  buzDebugCode += `\n\n\n// copy from ${entryItem}\n\n\n`
  buzDebugCode += buzAfter;
});




//拼接需要测试的buz模块
fs.writeFileSync('./index.js', buzDebugCode);
