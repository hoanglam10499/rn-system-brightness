#import "Brightness.h"

@implementation Brightness

RCT_EXPORT_MODULE();

+ (BOOL)requiresMainQueueSetup
{
    return NO;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(getBrightness:(RCTPromiseResolveBlock)resolve
                  getScreenBrightnessRejector:(RCTPromiseRejectBlock)reject) {

    float brightness = [[UIScreen mainScreen] brightness];
    resolve(@(brightness));
}

RCT_REMAP_METHOD(setBrightness,
                 brightnessLevel:(CGFloat)brightnessLevel
                 setSystemBrightnessResolver:(RCTPromiseResolveBlock)resolve
                 setSystemBrightnessRejector:(RCTPromiseRejectBlock)reject) {
    [[UIScreen mainScreen] setBrightness:brightnessLevel];
    resolve(@(brightnessLevel));
}


@end