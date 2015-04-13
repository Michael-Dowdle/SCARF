/*************************************************************************\
*                  Copyright (C) Michael Dowdle, 2015.                   *
*                                                                         *
* This program is free software. You may use, modify, and redistribute it *
* under the terms of the GNU General Public License as published by the   *
* Free Software Foundation, either version 3 or (at your option) any      *
* later version. This program is distributed without any warranty.  See   *
* the file COPYING.gpl-v3 for details.                                    *
\*************************************************************************/

/* condition_containing_assignment.c

   Assign a variable value to the value of another variable inside a 
   condtion.

*/

int main(int argc, char *argv[])
{
	
	int a, b;
	bool same;
	
	a = 1;
	b = 2;
	same = false;
	
	if(a == b)
	{
		same = false;
	}
	
	if(a = b)
	{
		same = true;
	}
}