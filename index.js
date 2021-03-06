import {AppRegistry} from 'react-native';



// copy from ./biz/biz1/index.js


/** @format */

import {} from 'react-native';
import App from './biz/biz1/./App';
import {name as appName} from './biz/biz1/../../app.json';

AppRegistry.registerComponent(appName, () => App);



// copy from ./biz/biz2/index.js


/** @format */

import {} from 'react-native';
import App2 from './biz/biz2/./App';

AppRegistry.registerComponent("reactnative_multibundler2", () => App2);



// copy from ./biz/biz3/index.js


/** @format */

import {} from 'react-native';
import React, {Component} from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import {App3_1,App3_2} from "./biz/biz3/./App";

const Stack = createStackNavigator();

function App3() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="App3_1" component={App3_1} />
        <Stack.Screen name="App3_2" component={App3_2} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

AppRegistry.registerComponent("reactnative_multibundler3", () => App3);
