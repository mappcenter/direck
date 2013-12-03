//
//  locationCell.m
//  Direckv01
//
//  Created by Ruby on 12/2/13.
//  Copyright (c) 2013 Ruby. All rights reserved.
//

#import "locationCell.h"

@implementation locationCell

@synthesize nameLabel = _nameLabel;
@synthesize datetimeLabel = _datetimeLabel;
@synthesize imageView = _imageView;


- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
