package com.atechit.ar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArFragment arFragment;
    private ArrayList<Integer> imagesPath = new ArrayList<Integer>();
    private ArrayList<String> namesPath = new ArrayList<>();
    private ArrayList<String> modelNames = new ArrayList<>();
    AnchorNode anchorNode;
    private Button btnRemove;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        btnRemove = findViewById(R.id.remove);
        getImages();

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            Anchor anchor = hitResult.createAnchor();

            ModelRenderable.builder()
                .setSource(this,Uri.parse(Common.model))
                .build()
                .thenAccept(modelRenderable -> addModelToScene(anchor,modelRenderable));

            }) ;



        btnRemove.setOnClickListener (view -> removeAnchorNode(anchorNode));

    }

    private void getImages() {

        imagesPath.add(R.drawable.table);//1
        imagesPath.add(R.drawable.bookshelf);//2
        imagesPath.add(R.drawable.lamp);//3
        imagesPath.add(R.drawable.odltv);//4
        //imagesPath.add(R.drawable.clothdryer);//5
        imagesPath.add(R.drawable.chair);//6
        imagesPath.add(R.drawable.andy);//7
        imagesPath.add(R.drawable.smalllamp);//8
        namesPath.add("Table");//1
        namesPath.add("BookShelf");//2
        namesPath.add("Lamp");//3
        namesPath.add("Old Tv");//4
        //namesPath.add("Cloth");//5
        namesPath.add("Chair");//6
        namesPath.add("andy");//7
        namesPath.add("smalllamp");//8
        modelNames.add("table.sfb");//1
        modelNames.add("model.sfb");//2
        modelNames.add("lamp.sfb");//3
        modelNames.add("tv.sfb");//4
       // modelNames.add("clothe.sfb");//5
        modelNames.add("chair.sfb");//6
        modelNames.add("andy.sfb");//7
        modelNames.add("smalllamp");

        initaiteRecyclerview();
    }

    private void initaiteRecyclerview() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this,namesPath,imagesPath,modelNames);
        recyclerView.setAdapter(adapter);

    }

    public void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {

        anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public void removeAnchorNode (AnchorNode nodeToremove) {

        if (nodeToremove != null)
        {
        arFragment.getArSceneView().getScene().removeChild(nodeToremove);
        nodeToremove.getAnchor().detach();
        nodeToremove.setParent(null);
        nodeToremove = null;
        }

    }


/*
public void handleOnTouch(HitTestResult hitTestResult, MotionEvent motionEvent)
{
 First call ArFragment's listener to handle TransformableNodes.


        arFragment.onPeekTouch(hitTestResult, motionEvent);

        // Check for touching a Sceneform node
        if (hitTestResult.getNode() != null) {

            Node hitNode = hitTestResult.getNode();

            if (hitNode.getRenderable() == modelRenderable) {
                //DO whatever you need to here...

            }
            return;
        }
    }*/
}
