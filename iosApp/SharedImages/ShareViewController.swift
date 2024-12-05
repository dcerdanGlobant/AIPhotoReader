//
//  ShareViewController.swift
//  ShareActionImages
//
//  Created by Antonio Martinez Zarco on 3/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import Social
import SwiftUI

class ShareViewController: UIViewController {
        
    override func viewDidLoad() {
        super.viewDidLoad()
        
        isModalInPresentation = true
        
        if let itemProviders = (extensionContext!.inputItems.first as? NSExtensionItem)?.attachments {
            let hostingView = UIHostingController(rootView: ShareView(itemProviders: itemProviders, extensionContext: extensionContext))
            hostingView.view.frame = view.frame
            view.addSubview(hostingView.view)
        }
        
        
        
    }
    
}

fileprivate struct ShareView: View {
    
    var itemProviders:[NSItemProvider]
    var extensionContext: NSExtensionContext?
    @State private var items: [ImageItem] = []
    
    var body: some View {
        GeometryReader {
            let size = $0.size
            VStack(spacing: 15) {
                Text("Compartir imagen")
                    .frame(maxWidth: .infinity)
                    .overlay(alignment: .leading) {
                        Button("Cancel", action: dismiss)
                        .tint(.red)
                    }
                    .padding(.bottom, 10)
                
                if #available(iOS 17.0, *) {
                    ScrollView(.horizontal) {
                        LazyHStack(spacing: 10) {
                            ForEach(items) { item in
                                Image(uiImage: item.previewImage)
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: size.width - 30)
                            }
                        }
                        .padding(.horizontal, 15)
                        .scrollTargetLayout()
                        
                    }
                    .scrollTargetBehavior(.viewAligned)
                    .frame(height: 300)
                    
                    Button { saveItems() } label: {
                        Text("Get more info")
                            .fontWeight(.semibold)
                            .padding(.vertical, 10)
                            .frame(maxWidth: .infinity)
                            .foregroundStyle(.white)
                            .background(.blue, in: .rect(cornerRadius: 10))
                            .contentShape(.rect)
                    }

                    
                } else {
                    // Fallback on earlier versions
                }
                
                Spacer(minLength: 0)
            }
            .padding(15)
            .onAppear {
                extractItems(size: size)
            }
        }
    }
    
    func extractItems(size: CGSize) {
        guard items.isEmpty else {return}
        DispatchQueue.global(qos: .userInteractive).async {
            for provider in itemProviders {
                if #available(iOS 16.0, *) {
                    let _ = provider.loadDataRepresentation(for: .image) { data , error in
                        if let data, let image = UIImage(data: data), let thumbnail = image.preparingThumbnail(of: .init(width: size.width, height: 300)) {
                            
                            // UI Must be Updated on main thread
                            DispatchQueue.main.async {
                                items.append(.init(imageData: data, previewImage: thumbnail))
                            }
                        }
                    }
                } else {
                    // Fallback on earlier versions
                }
            }
        }
    }
    
    func dismiss() {
        extensionContext?.completeRequest(returningItems: [])
    }
    
    func saveItems() {
        if let url = URL(string: "AiPhotoReader://" ){
            extensionContext?.open(url, completionHandler: { success in
                if success {
                    dismiss()
                } else {
                    print("")
                }
            })
        }
    }
    
    private struct ImageItem: Identifiable {
        let id: UUID = .init()
        var imageData: Data
        var previewImage: UIImage
    }
}


