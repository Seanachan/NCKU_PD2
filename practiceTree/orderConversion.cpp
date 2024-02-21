
#include<iostream>
using namespace std;
class TreeNode{
    public:
    int val;
    struct TreeNode* left,*right;
    TreeNode(int value){
        val=value;
        left=NULL;
        right=NULL;
    }
};
typedef class TreeNode Node;
Node *rootNode=NULL;
int main(){
    
    return 0;
}
