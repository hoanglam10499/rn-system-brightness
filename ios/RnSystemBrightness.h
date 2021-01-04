#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#if __has_include(<React/RCTBridgeModule.H>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

@interface RnSystemBrightness : NSObject<RCTBridgeModule>

@end