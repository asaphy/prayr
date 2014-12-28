http://www.raywenderlich.com/78576/android-tutorial-for-beginners-part-2


TIP: CMD + SHIFT + O to navigate to any file in your project
Open MainActivity.java
Remove this function: onOptionsItemSelected

Open app/res/layout/activity_main.xml
Switch to text view
Delete padding lines
3 Forms:
wrap_content: view will be just big enought to fit whatever is inside
match_parent: view will be as big as its parent
explicit values: view will be as big as you specifiy (use dp)

TIP: You can command click on the resource ID to be brought directly to the definition in your resource file.

android:layout_alignParentBottom="true"
Add margin:
android:layout_marginTop="20dp"


Switch to LinearLayout
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">
</LinearLayout>
```

Right-click and refactor variable names

Add TextView mainTextView; to MainActivity.java
Import by option + enter

Add to on create
// 1. Access the TextView defined in layout XML
// and then set its text
mainTextView = (TextView) findViewById(R.id.main_textview);
mainTextView.setText("Set in Java!");


You added an id attribute to the View in XML.
You used the id to access the View via your code.
You called a method on the View to change its text value.

Add button in activity_main.xml
<!-- Set OnClickListener to trigger results when pressed -->
<Button
    android:id="@+id/main_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"
    android:layout_marginLeft="20dp"
    android:text="@string/button" />

Add line in strings.xml
<string name="button">Update TextView</string>

Add button to Java
Button mainButton;

Add to on create
// 2. Access the Button defined in layout XML
// and listen for it here
mainButton = (Button) findViewById(R.id.main_button);
mainButton.setOnClickListener(this);

Add this method
@Override
    public void onClick(View view) {
      mainTextView.setText("Button pressed!");
    }
