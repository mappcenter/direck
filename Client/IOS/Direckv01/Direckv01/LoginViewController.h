//
//  LoginViewController.h
//  Direckv01
//
//  Created by Ruby on 11/29/13.
//  Copyright (c) 2013 Ruby. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LoginViewController : UIViewController{
    IBOutlet UITextField *phoneNumber;
    IBOutlet UITextField *displayName;
}

- (IBAction)registerMember;
- (void)directToHomeScreen;
@end
