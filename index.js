import { NativeModules, Platform } from "react-native";
const { Brightness } = NativeModules;

export default {
  getMaxBrightness: async () => {
    if (Platform.OS === "android") {
      return await Brightness.getMaxBrightness();
    }
    return 1;
  },
  getBrightness: async () => {
    const brightness = await Brightness.getBrightness();
    if (Platform.OS === "android") {
      const MAX = await Brightness.getMaxBrightness();
      return brightness / MAX;
    }
    return brightness;
  },
  setBrightness: async (value) => {
    if (Platform.OS === "android") {
      const MAX = await Brightness.getMaxBrightness();
      await Brightness.setBrightness(value * MAX);
    } else {
      await Brightness.setBrightness(value);
    }
  },
  // Permission
  hasPermission: async () => {
    if (Platform.OS === "android") {
      return await Brightness.hasPermission();
    }
  },
  requestPermission: async () => {
    if (Platform.OS === "android") {
      return await Brightness.requestPermission();
    }
  },
};
