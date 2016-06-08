/** 
 * @author: CharMaker by RSoft
 * 
 * This file represents the font font
 * Character count:4
 * Character height:5
 * Character width (0 is variable):5
 * 
 * Rotation: 90
 * Mirrored horizontally: false
 * Mirrored vertically: true
 */
unsigned char character_123[5] = {0x01, 0x02, 0x04, 0x08, 0x10}	//  
// 	.	.	.	.	#	
// 	.	.	.	#	.	
// 	.	.	#	.	.	
// 	.	#	.	.	.	
// 	#	.	.	.	.	
// 	
unsigned char character_321[5] = {0x10, 0x08, 0x04, 0x02, 0x01}	// !
// 	#	.	.	.	.	
// 	.	#	.	.	.	
// 	.	.	#	.	.	
// 	.	.	.	#	.	
// 	.	.	.	.	#	
// 	
unsigned char character_up[5] = {0x04, 0x0e, 0x15, 0x04, 0x04}	// "
// 	.	.	#	.	.	
// 	.	.	.	#	.	
// 	#	#	#	#	#	
// 	.	.	.	#	.	
// 	.	.	#	.	.	
// 	
unsigned char character_down[5] = {0x04, 0x04, 0x15, 0x0e, 0x04}	// #
// 	.	.	#	.	.	
// 	.	#	.	.	.	
// 	#	#	#	#	#	
// 	.	#	.	.	.	
// 	.	.	#	.	.	
// 	
unsigned char* font[4] = {
character_123,
character_321,
character_up,
character_down
};
