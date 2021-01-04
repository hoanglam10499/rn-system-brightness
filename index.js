import { NativeModules, Platform } from "react-native";
const RNSystemBrightness = NativeModules.RNSystemBrightness;

export default {
  getMaxBrightness: async () => {
    if (Platform.OS === "android") {
      return await RNSystemBrightness.getMaxBrightness();
    }
    return 1;
  },
  getBrightness: async () => {
    const brightness = await RNSystemBrightness.getBrightness();
    if (Platform.OS === "android") {
      const MAX = await RNSystemBrightness.getMaxBrightness();
      return brightness / MAX;
    }
    return brightness;
  },
  setBrightness: async (value) => {
    if (Platform.OS === "android") {
      const MAX = await RNSystemBrightness.getMaxBrightness();
      await RNSystemBrightness.setBrightness(value * MAX);
    } else {
      await RNSystemBrightness.setBrightness(value);
    }
  },
  // Permission
  hasPermission: async () => {
    if (Platform.OS === "android") {
      return await RNSystemBrightness.hasPermission();
    }
  },
  requestPermission: async () => {
    if (Platform.OS === "android") {
      return await RNSystemBrightness.requestPermission();
    }
  },
};
