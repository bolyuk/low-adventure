package com.atr.lowadventure.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.util.*;

import java.util.*;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.content.ClipData;
import android.view.View;

import com.atr.lowadventure.FileUtil;
import com.atr.lowadventure.R;
import com.atr.lowadventure.SketchwareUtil;

import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class SignPageActivity extends AppCompatActivity {
	
	public final int REQ_CD_A = 101;
	
	private LinearLayout linear1;
	private LinearLayout linear40;
	private ScrollView vscroll2;
	private LinearLayout linear12;
	private ImageView exit;
	private LinearLayout logo_childlinear;
	private ImageView logo_image;
	private TextView logo_text;
	private ProgressBar progressbar1;
	private LinearLayout user_linear;
	private LinearLayout linear13;
	private LinearLayout searchlinear;
	private LinearLayout linear38;
	private LinearLayout settings;
	private LinearLayout settings_linear;
	private LinearLayout passedit;
	private LinearLayout passedit_linear;
	private LinearLayout mailedit;
	private LinearLayout mailedit_linear;
	private LinearLayout linear36;
	private LinearLayout linear16;
	private ImageView photo;
	private LinearLayout linear37;
	private TextView name;
	private TextView access;
	private ImageView search;
	private ImageView copy;
	private ImageView logout;
	private LinearLayout linear44;
	private LinearLayout linear45;
	private ImageView imageview19;
	private EditText search_edt;
	private ImageView search_b;
	private TextView lvl;
	private ProgressBar progressbar2;
	private TextView textview16;
	private ImageView settings_img;
	private LinearLayout linear41;
	private LinearLayout linear34;
	private Button settings_button1;
	private ImageView newava_img;
	private LinearLayout linear42;
	private Button newava_b1;
	private LinearLayout linear43;
	private Button newava_b2;
	private ImageView imageview17;
	private EditText settings_edt1;
	private TextView textview1;
	private ImageView passedit_img;
	private LinearLayout linear33;
	private LinearLayout linear35;
	private Button passedit_button;
	private ImageView imageview1;
	private EditText passedit_edt1;
	private ImageView imageview18;
	private EditText passedit_edt2;
	private TextView textview15;
	private ImageView mailedit_img;
	private LinearLayout linear30;
	private LinearLayout linear31;
	private Button mailedit_button;
	private ImageView imageview16;
	private EditText mailedit_edt1;
	private ImageView imageview3;
	private EditText mailedit_edt2;
	private ScrollView vscroll1;
	private LinearLayout linear11;
	private LinearLayout signin_linear;
	private LinearLayout linear9;
	private LinearLayout no_sign;
	private LinearLayout sign;
	private LinearLayout sign_linear;
	private LinearLayout reg;
	private LinearLayout reg_linear;
	private LinearLayout reset;
	private LinearLayout reset_linear;
	private TextView textview12;
	private ImageView sign_img;
	private LinearLayout linear24;
	private LinearLayout linear25;
	private Button sign_button;
	private ImageView imageview14;
	private EditText sign_edt1;
	private ImageView imageview15;
	private EditText sign_edt2;
	private TextView textview13;
	private ImageView reg_img;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private Button reg_button;
	private ImageView imageview10;
	private EditText reg_edt1;
	private ImageView imageview2;
	private EditText reg_edt2;
	private ImageView imageview11;
	private EditText reg_edt3;
	private TextView textview14;
	private ImageView reset_img;
	private LinearLayout linear7;
	private Button reset_button;
	private ImageView imageview12;
	private EditText reset_edt1;
	private TextView description;
	private TextView menu;
	private TextView version;
	
	private Intent a = new Intent(Intent.ACTION_GET_CONTENT);
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.sign_page);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear40 = (LinearLayout) findViewById(R.id.linear40);
		vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		exit = (ImageView) findViewById(R.id.exit);
		logo_childlinear = (LinearLayout) findViewById(R.id.logo_childlinear);
		logo_image = (ImageView) findViewById(R.id.logo_image);
		logo_text = (TextView) findViewById(R.id.logo_text);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		user_linear = (LinearLayout) findViewById(R.id.user_linear);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		searchlinear = (LinearLayout) findViewById(R.id.searchlinear);
		linear38 = (LinearLayout) findViewById(R.id.linear38);
		settings = (LinearLayout) findViewById(R.id.settings);
		settings_linear = (LinearLayout) findViewById(R.id.settings_linear);
		passedit = (LinearLayout) findViewById(R.id.passedit);
		passedit_linear = (LinearLayout) findViewById(R.id.passedit_linear);
		mailedit = (LinearLayout) findViewById(R.id.mailedit);
		mailedit_linear = (LinearLayout) findViewById(R.id.mailedit_linear);
		linear36 = (LinearLayout) findViewById(R.id.linear36);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		photo = (ImageView) findViewById(R.id.photo);
		linear37 = (LinearLayout) findViewById(R.id.linear37);
		name = (TextView) findViewById(R.id.name);
		access = (TextView) findViewById(R.id.access);
		search = (ImageView) findViewById(R.id.search);
		copy = (ImageView) findViewById(R.id.copy);
		logout = (ImageView) findViewById(R.id.logout);
		linear44 = (LinearLayout) findViewById(R.id.linear44);
		linear45 = (LinearLayout) findViewById(R.id.linear45);
		imageview19 = (ImageView) findViewById(R.id.imageview19);
		search_edt = (EditText) findViewById(R.id.search_edt);
		search_b = (ImageView) findViewById(R.id.search_b);
		lvl = (TextView) findViewById(R.id.lvl);
		progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
		textview16 = (TextView) findViewById(R.id.textview16);
		settings_img = (ImageView) findViewById(R.id.settings_img);
		linear41 = (LinearLayout) findViewById(R.id.linear41);
		linear34 = (LinearLayout) findViewById(R.id.linear34);
		settings_button1 = (Button) findViewById(R.id.settings_button1);
		newava_img = (ImageView) findViewById(R.id.newava_img);
		linear42 = (LinearLayout) findViewById(R.id.linear42);
		newava_b1 = (Button) findViewById(R.id.newava_b1);
		linear43 = (LinearLayout) findViewById(R.id.linear43);
		newava_b2 = (Button) findViewById(R.id.newava_b2);
		imageview17 = (ImageView) findViewById(R.id.imageview17);
		settings_edt1 = (EditText) findViewById(R.id.settings_edt1);
		textview1 = (TextView) findViewById(R.id.textview1);
		passedit_img = (ImageView) findViewById(R.id.passedit_img);
		linear33 = (LinearLayout) findViewById(R.id.linear33);
		linear35 = (LinearLayout) findViewById(R.id.linear35);
		passedit_button = (Button) findViewById(R.id.passedit_button);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		passedit_edt1 = (EditText) findViewById(R.id.passedit_edt1);
		imageview18 = (ImageView) findViewById(R.id.imageview18);
		passedit_edt2 = (EditText) findViewById(R.id.passedit_edt2);
		textview15 = (TextView) findViewById(R.id.textview15);
		mailedit_img = (ImageView) findViewById(R.id.mailedit_img);
		linear30 = (LinearLayout) findViewById(R.id.linear30);
		linear31 = (LinearLayout) findViewById(R.id.linear31);
		mailedit_button = (Button) findViewById(R.id.mailedit_button);
		imageview16 = (ImageView) findViewById(R.id.imageview16);
		mailedit_edt1 = (EditText) findViewById(R.id.mailedit_edt1);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		mailedit_edt2 = (EditText) findViewById(R.id.mailedit_edt2);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		signin_linear = (LinearLayout) findViewById(R.id.signin_linear);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		no_sign = (LinearLayout) findViewById(R.id.no_sign);
		sign = (LinearLayout) findViewById(R.id.sign);
		sign_linear = (LinearLayout) findViewById(R.id.sign_linear);
		reg = (LinearLayout) findViewById(R.id.reg);
		reg_linear = (LinearLayout) findViewById(R.id.reg_linear);
		reset = (LinearLayout) findViewById(R.id.reset);
		reset_linear = (LinearLayout) findViewById(R.id.reset_linear);
		textview12 = (TextView) findViewById(R.id.textview12);
		sign_img = (ImageView) findViewById(R.id.sign_img);
		linear24 = (LinearLayout) findViewById(R.id.linear24);
		linear25 = (LinearLayout) findViewById(R.id.linear25);
		sign_button = (Button) findViewById(R.id.sign_button);
		imageview14 = (ImageView) findViewById(R.id.imageview14);
		sign_edt1 = (EditText) findViewById(R.id.sign_edt1);
		imageview15 = (ImageView) findViewById(R.id.imageview15);
		sign_edt2 = (EditText) findViewById(R.id.sign_edt2);
		textview13 = (TextView) findViewById(R.id.textview13);
		reg_img = (ImageView) findViewById(R.id.reg_img);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		reg_button = (Button) findViewById(R.id.reg_button);
		imageview10 = (ImageView) findViewById(R.id.imageview10);
		reg_edt1 = (EditText) findViewById(R.id.reg_edt1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		reg_edt2 = (EditText) findViewById(R.id.reg_edt2);
		imageview11 = (ImageView) findViewById(R.id.imageview11);
		reg_edt3 = (EditText) findViewById(R.id.reg_edt3);
		textview14 = (TextView) findViewById(R.id.textview14);
		reset_img = (ImageView) findViewById(R.id.reset_img);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		reset_button = (Button) findViewById(R.id.reset_button);
		imageview12 = (ImageView) findViewById(R.id.imageview12);
		reset_edt1 = (EditText) findViewById(R.id.reset_edt1);
		description = (TextView) findViewById(R.id.description);
		menu = (TextView) findViewById(R.id.menu);
		version = (TextView) findViewById(R.id.version);
		a.setType("*/*");
		a.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), GameActivity.class);
				startActivity(intent);
			}
		});
		
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(settings_img.getRotation()== 0)
				{
					settings_linear.setVisibility(View.VISIBLE);
					settings_img.setRotation(180);
					mailedit_linear.setVisibility(View.GONE);
					mailedit_img.setRotation(0);
					passedit_linear.setVisibility(View.GONE);
					passedit_img.setRotation(0);
				} else {
					settings_linear.setVisibility(View.GONE);
					settings_img.setRotation(0);
				}
			}
		});
		
		passedit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(passedit_img.getRotation()== 0)
				{
					passedit_linear.setVisibility(View.VISIBLE);
					passedit_img.setRotation(180);
					settings_linear.setVisibility(View.GONE);
					settings_img.setRotation(0);
					mailedit_linear.setVisibility(View.GONE);
					mailedit_img.setRotation(0);
				} else {
					passedit_linear.setVisibility(View.GONE);
					passedit_img.setRotation(0);
				}
			}
		});
		
		mailedit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(mailedit_img.getRotation()== 0)
				{
					mailedit_linear.setVisibility(View.VISIBLE);
					mailedit_img.setRotation(180);
					settings_linear.setVisibility(View.GONE);
					settings_img.setRotation(0);
					passedit_linear.setVisibility(View.GONE);
					passedit_img.setRotation(0);
				} else {
					mailedit_linear.setVisibility(View.GONE);
					mailedit_img.setRotation(0);
				}
			}
		});
		
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(searchBool)
				{
					searchBool=false;
					searchlinear.setVisibility(View.GONE);
					search.setImageResource(R.drawable.search);
				} else {
					searchBool=true;
					searchlinear.setVisibility(View.VISIBLE);
					search.setImageResource(R.drawable.cancel);
				}
			}
		});
		
		copy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				setClipboard(SignPageActivity.this, id);
				_toast("Айди скопировано!");
			}
		});
		
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(idS != null)
				{
					idS=null;
					start();
				} else {
					AlertDialog.Builder d = new AlertDialog.Builder(SignPageActivity.this);
					d.setCancelable(false);
					d.setIcon(R.drawable.atr_logo);
					d.setTitle("ATR ID");
					d.setMessage("Вы действительно хотите выйти из аккаунта?");
					d.setPositiveButton("Да", new DialogInterface.OnClickListener() 
					{
						@Override
						public void onClick(DialogInterface dialog, int arg1) 
						 {
							com.google.firebase.auth.FirebaseAuth.getInstance().signOut(); 
							start();
						}});
					d.setNegativeButton("Нет", new DialogInterface.OnClickListener() 
					{
						@Override
						public void onClick(DialogInterface dialog, int arg1) 
						 {
							
						}});
					d.show();
				}
			}
		});
		
		search_b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				idS=search_edt.getText().toString();
				start();
			}
		});
		
		settings_button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(settings_edt1.getText().toString().length() > 2 && settings_edt1.getText().toString().length() < 10)
				{
					user_linear.setVisibility(View.GONE);
					progressbar1.setVisibility(View.VISIBLE);
					com.google.firebase.database.DatabaseReference mDatabase = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
					
					mDatabase.child("users/"+com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid()+"/name").setValue(settings_edt1.getText().toString());
					 start();                      
				}else {
					settings_edt1.setError("Имя должно быть не меньше трёх символов и не больше десяти");
				}
			}
		});
		
		newava_b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Выберите фото"), 1);
			}
		});
		
		newava_b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		passedit_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(passedit_edt1.getText().toString().length() > 0)
				{
					if(passedit_edt2.getText().toString().length() > 0)
					{
						user_linear.setVisibility(View.GONE);
						progressbar1.setVisibility(View.VISIBLE);
						 user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
						com.google.firebase.auth.AuthCredential credential = com.google.firebase.auth.EmailAuthProvider
						        .getCredential(user.getEmail(), passedit_edt1.getText().toString());
						
						user.reauthenticate(credential)
						        .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
							            @Override
							            public void onComplete(com.google.android.gms.tasks.Task<Void> task) {
								                if (task.isSuccessful()) {
									                    user.updatePassword(passedit_edt2.getText().toString()).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
										                        @Override
										                        public void onComplete(com.google.android.gms.tasks.Task<Void> task) {
											                            if (task.isSuccessful()) {
												                              start();
												_toast("Успешно!");
												                            } else {
												user_linear.setVisibility(View.VISIBLE);
												progressbar1.setVisibility(View.GONE);                                passedit_edt1.setError(task.getException().getMessage().toString());
												                            }
											                        }
										                    });
									                } else {
									user_linear.setVisibility(View.VISIBLE);
									progressbar1.setVisibility(View.GONE);                    passedit_edt1.setError(task.getException().getMessage().toString());
									                }
								            }
							        });
					} else {
						passedit_edt2.setError("Пароль не может быть пустой!");
					}
				} else {
					passedit_edt1.setError("Пароль не может быть пустой!");
				}
			}
		});
		
		mailedit_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(mailedit_edt1.getText().toString().length() > 0)
				{
					if(mailedit_edt2.getText().toString().length() > 0)
					{
						user_linear.setVisibility(View.GONE);
						progressbar1.setVisibility(View.VISIBLE);
						 user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
						com.google.firebase.auth.AuthCredential credential = com.google.firebase.auth.EmailAuthProvider
						        .getCredential(user.getEmail(), mailedit_edt2.getText().toString());
						
						user.reauthenticate(credential)
						        .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
							            @Override
							            public void onComplete(com.google.android.gms.tasks.Task<Void> task) {
								                if (task.isSuccessful()) {
									                    user.updateEmail(mailedit_edt1.getText().toString()).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
										                        @Override
										                        public void onComplete(com.google.android.gms.tasks.Task<Void> task) {
											                            if (task.isSuccessful()) {
												                              start();
												_toast("Успешно!");
												                            } else {
												user_linear.setVisibility(View.VISIBLE);
												progressbar1.setVisibility(View.GONE);                                mailedit_edt1.setError(task.getException().getMessage().toString());
												                            }
											                        }
										                    });
									                } else {
									                   user_linear.setVisibility(View.VISIBLE);
									progressbar1.setVisibility(View.GONE); mailedit_edt1.setError(task.getException().getMessage().toString());
									                }
								            }
							        });
					} else {
						mailedit_edt2.setError("Пароль не может быть пустой!");
					}
				} else {
					mailedit_edt1.setError("Почта не может быть пустой!");
				}
			}
		});
		
		sign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(sign_img.getRotation()== 0)
				{
					sign_linear.setVisibility(View.VISIBLE);
					sign_img.setRotation(180);
					reg_linear.setVisibility(View.GONE);
					reg_img.setRotation(0);
					reset_linear.setVisibility(View.GONE);
					reset_img.setRotation(0);
				} else {
					sign_linear.setVisibility(View.GONE);
					sign_img.setRotation(0);
				}
			}
		});
		
		reg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(reg_img.getRotation() == 0)
				{
					sign_linear.setVisibility(View.GONE);
					sign_img.setRotation(0);
					reg_linear.setVisibility(View.VISIBLE);
					reg_img.setRotation(180);
					reset_linear.setVisibility(View.GONE);
					reset_img.setRotation(0);
				} else {
					reg_linear.setVisibility(View.GONE);
					reg_img.setRotation(0);
				}
			}
		});
		
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(reset_img.getRotation() == 0)
				{
					sign_linear.setVisibility(View.GONE);
					sign_img.setRotation(0);
					reg_linear.setVisibility(View.GONE);
					reg_img.setRotation(0);
					reset_linear.setVisibility(View.VISIBLE);
					reset_img.setRotation(180);
				} else {
					reset_linear.setVisibility(View.GONE);
					reset_img.setRotation(0);
				}
			}
		});
		
		sign_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(sign_edt1.getText().toString().length() > 0)
				{
					if(sign_edt2.getText().toString().length() > 0)
					{
						
						com.google.firebase.auth.FirebaseAuth mAuth = com.google.firebase.auth.FirebaseAuth.getInstance(); 
						signin_linear.setVisibility(View.GONE);
						progressbar1.setVisibility(View.VISIBLE);
						mAuth.signInWithEmailAndPassword(sign_edt1.getText().toString(), sign_edt2.getText().toString()).addOnCompleteListener( 
						                new com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>() { 
							                    @Override
							                    public void onComplete( 
							                        com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) 
							                    { 
								                        if (task.isSuccessful()) { 
									  
									 start();                      
									                        } 
								  
								                        else { 
									signin_linear.setVisibility(View.VISIBLE);
									progressbar1.setVisibility(View.GONE); sign_edt1.setError(task.getException().getMessage().toString());
									                        } 
								                    } 
							                }); 
					} else {
						sign_edt2.setError("Пароль не может быть пустой!");
					}
				} else {
					sign_edt1.setError("Почта не может быть пустой!");
				}
			}
		});
		
		reg_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(reg_edt1.getText().toString().length() > 2 && reg_edt1.getText().toString().length() < 10)
				{
					if(reg_edt2.getText().toString().length() > 0)
					{
						if(reg_edt3.getText().toString().length() > 0)
						{
							com.google.firebase.auth.FirebaseAuth mAuth = com.google.firebase.auth.FirebaseAuth.getInstance(); 
							signin_linear.setVisibility(View.GONE);
							progressbar1.setVisibility(View.VISIBLE);
							mAuth.createUserWithEmailAndPassword(reg_edt2.getText().toString(),reg_edt3.getText().toString())
							                .addOnCompleteListener( 
							                new com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>() { 
								                    @Override
								                    public void onComplete( 
								                        com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) 
								                    { 
									                        if (task.isSuccessful()) { 
										  com.google.firebase.database.DatabaseReference mDatabase = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("users/");
										
										mDatabase.child(com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid()+"/name").setValue(reg_edt1.getText().toString());
										 start();                      
										                        } 
									  
									                        else { 
										signin_linear.setVisibility(View.VISIBLE);
										progressbar1.setVisibility(View.GONE); reg_edt1.setError(task.getException().getMessage().toString());
										                        } 
									                    } 
								                }); 
						} else {
							reg_edt3.setError("Пароль не может быть пустой");
						}
					} else {
						reg_edt2.setError("Почта не может быть пустой");
					}
				} else {
					reg_edt1.setError("Имя должно быть не меньше трёх символов и не больше десяти");
				}
			}
		});
		
		reset_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if(reset_edt1.getText().toString().length() > 0)
				{
					signin_linear.setVisibility(View.GONE);
					progressbar1.setVisibility(View.VISIBLE);
					com.google.firebase.auth.FirebaseAuth.getInstance().sendPasswordResetEmail(reset_edt1.getText().toString())
					    .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
						        @Override
						        public void onComplete( com.google.android.gms.tasks.Task<Void> task) {
							            if (task.isSuccessful()) {
								_toast("Успешно! проверьте почту");
								                start();
								            } else {
								reset_edt1.setError(task.getException().getMessage().toString());
								signin_linear.setVisibility(View.VISIBLE);
								progressbar1.setVisibility(View.GONE);
							}
							        }
						    });
				} else {
					reset_edt1.setError("Почта не может быть пустой");
				}
			}
		});
		
		description.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
				AlertDialog.Builder d = new AlertDialog.Builder(SignPageActivity.this);
				d.setCancelable(false);
				d.setIcon(R.drawable.atr_logo);
				d.setTitle("ATR ID");
				d.setMessage("ATR ID - аккаунт который никогда не потеряется! Один аккаунт для всех приложений (и будущих тоже) от ATR. Синхронизация в разных приложений всего вашего прогресса! \n Использовать аккаунт не обязательно!");
				d.setPositiveButton("Понятно", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int arg1) 
					 {
						
					}});
				d.show();
			}
		});
		
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), GameActivity.class);
				startActivity(intent);
			}
		});
	}
	private void initializeLogic() {
		idS = getIntent().getStringExtra("id");
		start();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_A:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
			}}
		 try{
			   if (_requestCode == 1 && _resultCode == Activity.RESULT_OK) {
				 uri = _data.getData();
				
				BitmapFactory.Options o = new BitmapFactory.Options();
				        o.inJustDecodeBounds = true;
				        BitmapFactory.decodeStream(
				        SignPageActivity.this.getContentResolver().openInputStream(uri),
				        null,
				        o);
				 if(o.outHeight == o.outWidth){
					newava_img.setImageURI(uri);
				} else {
					
					        cropIntent = new Intent("com.android.camera.action.CROP");
					
					        cropIntent.setData(uri);           
					        cropIntent.putExtra("crop", "true");           
					        cropIntent.putExtra("aspectX", 1);
					        cropIntent.putExtra("aspectY", 1);           
					        cropIntent.putExtra("outputX", 128);
					        cropIntent.putExtra("outputY", 128);           
					        cropIntent.putExtra("return-data", true);
					        startActivityForResult(cropIntent, 2);
					    
				}
				    } else if (_requestCode == 2) {
				uri = _data.getData();
				newava_img.setImageURI(uri);
			}
		}catch(Exception err){_toast(err.toString());}
		switch(0){case 1: if(false){
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	private void _toast (final String _txt) {
		SketchwareUtil.showMessage(getApplicationContext(), _txt);
	}
	
	
	private void _extra () {
		
	}
	public void clearEditText(View start){
		try{
			ViewGroup v = (ViewGroup) start;
			for( int i = 0; i < v.getChildCount(); i++ ){
				 if( v.getChildAt( i ) instanceof EditText)
				    {
					((EditText)v.getChildAt( i )).setText("");
					    } else if(v.getChildAt( i ) instanceof LinearLayout || v.getChildAt( i ) instanceof ScrollView) 
				{
					clearEditText(v.getChildAt(i));
				}
			}
		}catch(Exception err){}
	}
	public Object isValid(com.google.firebase.database.DataSnapshot ds, String key, Object elseValue)
	{
		if(ds.hasChild(key))
		{
			return ds.child(key).getValue(Object.class);
		} else {
			return elseValue;
		}
	}
	com.google.firebase.auth.FirebaseUser user;
	Uri uri;
	String id;
	String idS = null;
	Intent cropIntent;
	boolean searchBool = false;
	private void setClipboard(Context context, String text) {
		  if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			    clipboard.setText(text);
			  } else {
			    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
			    clipboard.setPrimaryClip(clip);
			  }
	}
	public void access(TextView txt, long access)
	{
		switch((int)access){
			case -1:
			txt.setText("Аккаунт заблокирован");
			case 0:
			txt.setText("Пользователь");
			break;
			case 1:
			txt.setText("Вип");
			break;
			case 2:
			txt.setText("Редактор");
			break;
			case 3:
			txt.setText("Тестер");
			break;
			case 4:
			txt.setText("Администратор");
			break;
			case 5:
			txt.setText("Создатель");
			break;
		}
	}
	public void start()
	{
		searchBool=false;
		search.setVisibility(View.VISIBLE);
		search.setImageResource(R.drawable.ic_search_black);
		searchlinear.setVisibility(View.GONE);
		progressbar1.setVisibility(View.GONE);
		logout.setVisibility(View.VISIBLE);
		logout.setImageResource(R.drawable.exit);
		vscroll2.setVisibility(View.GONE);
		
		clearEditText(linear1);
		
		settings_linear.setVisibility(View.GONE);
		settings_img.setRotation(0);
		mailedit_linear.setVisibility(View.GONE);
		mailedit_img.setRotation(0);
		passedit_linear.setVisibility(View.GONE);
		passedit_img.setRotation(0);
		
		sign_linear.setVisibility(View.GONE);
		sign_img.setRotation(0);
		reg_linear.setVisibility(View.GONE);
		reg_img.setRotation(0);
		reset_linear.setVisibility(View.GONE);
		reset_img.setRotation(0);
		if(idS != null)
		{
			id=idS;
			vscroll2.setVisibility(View.VISIBLE);
			search.setVisibility(View.GONE);
			settings.setVisibility(View.GONE);
			mailedit.setVisibility(View.GONE);
			passedit.setVisibility(View.GONE);
			signin_linear.setVisibility(View.GONE);
			logout.setImageResource(R.drawable.cancel);
		}else if (com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser() != null) 
		{
			vscroll2.setVisibility(View.VISIBLE);
			id = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
			settings.setVisibility(View.VISIBLE);
			mailedit.setVisibility(View.VISIBLE);
			passedit.setVisibility(View.VISIBLE);
			signin_linear.setVisibility(View.GONE);
		} else {
			signin_linear.setVisibility(View.VISIBLE);
			sign_linear.setVisibility(View.VISIBLE);
			vscroll2.setVisibility(View.GONE);
		}
		
		
		if(id != null)
		{
			
			progressbar1.setVisibility(View.VISIBLE);
			com.google.firebase.database.DatabaseReference db1 = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("users/"+id);
			db1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
				  @Override
				  public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) 
				{
					
					access(access, (long) isValid(dataSnapshot, "access",(long) 0));
					if((long)isValid(dataSnapshot, "xp",(long) 0) >= (long)isValid(dataSnapshot, "lvl",(long) 1)*15)
					{
						com.google.firebase.database.DatabaseReference mDatabase = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
						mDatabase.child("users/"+uri+"/xp").setValue((long)isValid(dataSnapshot, "xp",(long) 1)-(long)isValid(dataSnapshot, "lvl",(long) 1)*15);
						mDatabase.child("users/"+uri+"/lvl").setValue((long)isValid(dataSnapshot, "lvl",(long) 1)+1);
						start();
					} else {
						progressbar2.setProgress((int)(long)isValid(dataSnapshot, "xp",(long) 0));
						progressbar2.setMax((int)(long)isValid(dataSnapshot, "lvl",(long) 1)*15);
						lvl.setText("LVL "+Long.toString((long)isValid(dataSnapshot, "lvl",(long) 1)));
					}
					name.setText((String)isValid(dataSnapshot, "name", "?"));
					
					if((String)isValid(dataSnapshot, "photoUrl", "") != "")
					{
						com.bumptech.glide.Glide.with(getApplicationContext()).load(Uri.parse((String)isValid(dataSnapshot, "photoUrl", ""))).into(photo);
						
						com.bumptech.glide.Glide.with(getApplicationContext()).load(Uri.parse((String)isValid(dataSnapshot, "photoUrl", ""))).into(newava_img);
						
					} else {
						photo.setImageResource(R.drawable.user_circle);
						newava_img.setImageResource(R.drawable.user_circle);
					}
					progressbar1.setVisibility(View.GONE);
					user_linear.setVisibility(View.VISIBLE);
				}
				@Override
				public void onCancelled(com.google.firebase.database.DatabaseError databaseError) 
				{}});
		} 
	}
	{
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
