//
//  HomeViewController.h
//  Direckv01
//
//  Created by Ruby on 12/2/13.
//  Copyright (c) 2013 Ruby. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HomeViewController :  UITableViewController <UITableViewDelegate, UITableViewDataSource>

@property NSMutableArray *items;
@end
