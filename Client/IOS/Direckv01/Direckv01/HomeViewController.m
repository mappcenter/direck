//
//  HomeViewController.m
//  Direckv01
//
//  Created by Ruby on 12/2/13.
//  Copyright (c) 2013 Ruby. All rights reserved.
//

#import "HomeViewController.h"
#import "LocationItem.h"
#import "locationCell.h"

@interface HomeViewController ()

@end

@implementation HomeViewController

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
    self.items = [[NSMutableArray alloc] init];
    [self loadLocationsFromServer];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.items count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *simpleTableIdentifier = @"LocationCell";
    
    NSLog(@"create cell");
    locationCell *cell = (locationCell *)[tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    if (cell == nil)
    {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"LocationCell" owner:self options:nil];
        cell = [nib objectAtIndex:0];
    }
    
    /*
    LocationItem *item =[self.items objectAtIndex:indexPath.row];
    cell.nameLabel.text = item.headerName;
    cell.datetimeLabel.text = item.createdDate;
    if([item.locationType  isEqual: @"0"]){
        cell.imageView.image = [UIImage imageNamed:@"imgCreated.png"];
    }
    else if ([item.locationType isEqual: @"1"]){
        cell.imageView.image = [UIImage imageNamed:@"imgShared.png"];
    }
    else{
        cell.imageView.image = [UIImage imageNamed:@"imgMarked.png"];
    }
     */
    
    
    return cell;
}

- (void)CreateRandomItem:(NSString *)name withType:(NSString *)locationType withDate:(NSString *)createdDate  {
    LocationItem *item1 = [[LocationItem alloc] init];
    item1.headerName = name;
    item1.locationType = locationType;
    item1.createdDate = createdDate;
    [self.items addObject:item1];
    NSLog(@"Add item");
}

- (void)loadLocationsFromServer {
    [self CreateRandomItem:@"Name1" withType:@"0" withDate:@"01-11-13 09:00:00"];
    [self CreateRandomItem:@"Name2" withType:@"1" withDate:@"01-11-13 09:01:00"];
    [self CreateRandomItem:@"Name3" withType:@"2" withDate:@"01-11-13 09:02:00"];
    [self CreateRandomItem:@"Name4" withType:@"0" withDate:@"01-11-13 09:03:00"];
    [self CreateRandomItem:@"Name5" withType:@"1" withDate:@"01-11-13 09:04:00"];
    [self CreateRandomItem:@"Name6" withType:@"2" withDate:@"01-11-13 09:05:00"];
    [self CreateRandomItem:@"Name7" withType:@"0" withDate:@"01-11-13 09:06:00"];
}

@end
