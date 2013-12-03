//
//  LoginViewController.m
//  Direckv01
//
//  Created by Ruby on 11/29/13.
//  Copyright (c) 2013 Ruby. All rights reserved.
//

#import "LoginViewController.h"
//#import "Security/KeychainItemWrapper.h"

@interface LoginViewController ()

@end

@implementation LoginViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    //KeychainItemWrapper *keychainItem = [[KeychainItemWrapper alloc] initWithIdentifier:@"YourAppLogin" accessGroup:nil];
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *direckUserId = [defaults objectForKey:@"direckUserId"];

    if(direckUserId.length > 0)
    {
        // direck to home page
        [self directToHomeScreen];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)registerMember
{
    // does register here...
    NSString *userId = @"1";
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:userId forKey:@"direckUserId"];
    [self directToHomeScreen];
    
    NSLog(@"Register member");
}

-(void)directToHomeScreen
{
   
}

@end
