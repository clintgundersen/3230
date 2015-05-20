/* 
 * File:   newmain.cpp
 * Author: http://www.gnu.org/software/libc/manual/html_node/Setuid-Program-Example.html
 *
 * 30.9 Setuid Program Example
 * 
 * Created on October 22, 2014, 11:07 AM
 */

/*Here’s an example showing how to set up a program that changes its effective 
 * user ID.
 * This is part of a game program called caber-toss that manipulates a file 
 * scores that should be writable only by the game program itself. The program 
 * assumes that its executable file will be installed with the setuid bit set 
 * and owned by the same user as the scores file. Typically, a system 
 * administrator will set up an account like games for this purpose.
 * 
 * The executable file is given mode 4755, so that doing an ‘ls -l’ on it 
 * produces output like:
 * 
 * -rwsr-xr-x   1 games    184422 Jul 30 15:17 caber-toss
 * 
 * The setuid bit shows up in the file modes as the ‘s’.
 * 
 * The scores file is given mode 644, and doing an ‘ls -l’ on it shows:
 * 
 * -rw-r--r--  1 games           0 Jul 31 15:33 scores
 * 
 * Here are the parts of the program that show how to set up the changed user 
 * ID. This program is conditionalized so that it makes use of the file IDs 
 * feature if it is supported, and otherwise uses setreuid to swap the effective 
 * and real user IDs.
*/

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

using namespace std;

/* Remember the effective and real UIDs. */
//static uid_t euid, ruid;

/* Restore the effective UID to its original value. */
void setTheUID()
{
    int status;
    #ifdef _POSIX_SAVED_IDS
    status = seteuid (euid);
    #else
    status = setreuid (ruid, euid);
    #endif
    if (status < 0) 
    { 
        fprintf (stderr, "Couldn't set uid.\n");
        exit (status);
    }
}



int main()
{
    /* Remember the real and effective user IDs.  */
    ruid = getuid ();
    euid = geteuid ();
    undo_setuid ();

    /* Do the game and record the score.  */
    …

    return 0;
}

